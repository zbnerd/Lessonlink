package com.lessonlink.api.order;

import com.lessonlink.domain.db.common.embedded.Address;
import com.lessonlink.domain.db.order.Order;
import com.lessonlink.domain.db.order.condition.OrderSearch;
import com.lessonlink.domain.db.order.enums.OrderStatus;
import com.lessonlink.repository.order.OrderRepository;
import com.lessonlink.repository.order.OrderRepositoryCustomImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepositoryCustomImpl orderRepositoryImpl;
    private final OrderRepository orderRepository;

    /**
     * beta-V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록, LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     */
    @GetMapping("/api/beta-v1/simple-orders")
    public Page<Order> ordersV1() {
        Page<Order> all = orderRepositoryImpl.findAll(new OrderSearch(), PageRequest.of(0, 20));
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return all;
    }

    /**
     * beta-V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     * -
     */

    @GetMapping("/api/beta-v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        Page<Order> orders = orderRepositoryImpl.findAll(new OrderSearch(), PageRequest.of(0, 20));
        return orders.stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
     */
    @GetMapping("/api/beta-v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepositoryImpl.findAllWithMemberDelivery();

        return orders.stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }


}
