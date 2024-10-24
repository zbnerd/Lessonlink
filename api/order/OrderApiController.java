package com.lessonlink.api.order;

import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.order.enums.OrderSearch;
import com.lessonlink.domain.order.enums.OrderStatus;
import com.lessonlink.repository.OrderRepository;
import com.lessonlink.repository.OrderRepositoryCustomImpl;
import com.lessonlink.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderRepositoryCustomImpl orderRepositoryCustomImpl;
    private final OrderService orderService;

    /**
     * beta-V1. 엔티티 직접 노출
     * - 주문 정보를 엔티티 형태로 직접 반환합니다.
     * - 강제 초기화를 통해 LAZY 로딩을 수행하여 필요한 데이터를 미리 로딩합니다.
     * - Hibernate5Module과 @JsonIgnore를 사용하여 양방향 관계 문제를 해결합니다.
     * - 비추천: 엔티티가 변경되면 API 스펙이 바뀔 수 있고, 응답 성능에 영향을 줄 수 있습니다.
     *
     * @return 모든 주문 엔티티의 리스트
     */

    @GetMapping("/api/beta-v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepositoryCustomImpl.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName()); //Lazy 강제초기화
        }
        return all;
    }

    /**
     * beta-V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 지연 로딩으로 인해 쿼리가 여러 번 실행될 수 있습니다(N+1 문제).
     * - 데이터 변환을 위한 OrderResponseDto 사용.
     *
     * @return 주문 정보를 담은 DTO 리스트
     */
    @GetMapping("/api/beta-v2/orders")
    public List<OrderResponseDto> ordersV2() {
        List<Order> orders = orderRepositoryCustomImpl.findAllByString(new OrderSearch());
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(toList());
    }

    /**
     * beta-V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join을 사용하여 쿼리 성능을 최적화합니다.
     * - 쿼리 한 번에 필요한 데이터를 모두 로딩하여 N+1 문제를 해결합니다.
     *
     * @return 주문 정보를 담은 DTO 리스트
     */
    @GetMapping("/api/beta-v3/orders")
    public List<OrderResponseDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(toList());
    }

    /**
     * V1. 페이징을 적용하여 주문 목록 조회
     * - 특정 페이지와 제한된 수의 결과를 반환합니다.
     * - 쿼리 최적화를 위해 fetch join을 사용한 메서드 호출.
     *
     * @param offset 페이지 오프셋 (기본값: 0)
     * @param limit 페이지당 결과 수 (기본값: 100)
     * @return 페이징된 주문 정보 목록
     */
    @GetMapping("/api/v1/orders")
    public Result ordersV3Page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit

    ) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Order> orders = orderRepository.findAllWithMemberDelivery(pageRequest);
        return new Result(orders.stream()
                .map(OrderResponseDto::new)
                .collect(toList()));
    }

    /**
     * 새로운 주문 생성
     * - 주문 요청 정보를 받아서 주문을 생성하고, 생성된 주문의 ID를 반환합니다.
     *
     * @param request 주문 요청 정보 (회원 ID, 상품 ID, 수량)
     * @return 생성된 주문 ID를 담은 응답 객체
     */
    @PostMapping("/api/v1/orders")
    public CreateOrderResponseDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto request
    ) {
        Long orderId = orderService.order(
                request.getMemberIdSecretKey(),
                request.getItemId(),
                request.getQuantity()
        );

        return new CreateOrderResponseDto(orderId);
    }

    /**
     * 주문 취소 요청
     * - 주문 ID를 받아 해당 주문을 취소하고, 취소된 주문의 정보를 반환합니다.
     *
     * @param orderId 취소할 주문의 ID
     * @return 취소된 주문의 ID와 상태를 담은 응답 객체
     */
    @PostMapping("/api/v1/orders/{orderId}/cancel")
    public CancelOrderResponseDto cancelOrder(
            @PathVariable @Valid Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return new CancelOrderResponseDto(orderId);
    }

    /**
     * 주문 응답 DTO
     * - 주문 정보와 주문 아이템 정보를 포함한 DTO 클래스입니다.
     */
    @Data
    static class OrderResponseDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemResponseDto> orderItems;
        public OrderResponseDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemResponseDto(orderItem))
                    .collect(toList());
        }
    }

    /**
     * 주문 아이템 응답 DTO
     * - 각 주문 항목의 정보를 담은 DTO 클래스입니다.
     */
    @Data
    static class OrderItemResponseDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량
        public OrderItemResponseDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getOrderQuantity();
        }
    }

    /**
     * 주문 생성 요청 DTO
     * - 주문 생성에 필요한 정보를 담고 있는 DTO 클래스입니다.
     */
    @Data
    static class CreateOrderRequestDto {
        private String memberIdSecretKey;
        private Long itemId;
        private int quantity;
    }

    /**
     * 주문 생성 응답 DTO
     * - 생성된 주문의 ID를 응답으로 반환합니다.
     */
    @Data
    @AllArgsConstructor
    static class CreateOrderResponseDto {
        private Long orderId;
    }

    /**
     * 주문 취소 응답 DTO
     * - 취소된 주문의 ID와 주문 상태를 담고 있습니다.
     */
    @Data
    class CancelOrderResponseDto {
        private Long orderId;
        private OrderStatus orderStatus;

        public CancelOrderResponseDto(Long orderId) {
            this.orderId = orderId;
            this.orderStatus = orderService.findOne(orderId).getStatus();
        }
    }

    /**
     * 결과 응답 래퍼 클래스
     * - 데이터를 래핑하여 클라이언트에 응답합니다.
     * - 제네릭을 사용하여 다양한 타입의 데이터를 반환할 수 있습니다.
     *
     * @param <T> 반환할 데이터 타입
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
