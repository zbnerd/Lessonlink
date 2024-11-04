package com.lessonlink.service;

import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.delivery.enums.DeliveryStatus;
import com.lessonlink.domain.item.Item;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.domain.order.condition.OrderSearch;
import com.lessonlink.repository.ItemRepository;
import com.lessonlink.repository.MemberRepository;
import com.lessonlink.repository.OrderRepository;
import com.lessonlink.repository.OrderRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRepositoryCustomImpl orderRepositoryCustomImpl;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(String memberIdSecretKey, Long itemId, int quantity) {

        //엔티티 조회
        Member member = memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다. ID: " + memberIdSecretKey));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("상품이 존재하지 않습니다. ID: " + itemId));

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setDeliveryInfo(
                member.getAddress(),
                DeliveryStatus.READY
        );

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), quantity);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /** 주문 취소 **/
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /** 배송시작시 배송중으로 전환 **/
    @Transactional
    public void startComp(Long orderId) {
        Order order = orderRepositoryCustomImpl.findOrderDeliveryByOrderId(orderId);
        Delivery delivery = order.getDelivery();
        delivery.setComp();
    }

    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("해당 주문 정보가 존재하지 않습니다. ID: " + orderId));
    }

    /** 주문 조회 **/
    public Page<Order> findAll(OrderSearch orderSearch, Pageable pageable) {
        return orderRepositoryCustomImpl.findAll(orderSearch, pageable);
    }

}
