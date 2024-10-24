package com.lessonlink.service;

import com.lessonlink.domain.delivery.Delivery;
import com.lessonlink.domain.delivery.enums.DeliveryStatus;
import com.lessonlink.domain.item.Item;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.OrderItem;
import com.lessonlink.repository.ItemRepository;
import com.lessonlink.repository.MemberRepository;
import com.lessonlink.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(String memberIdSecretKey, Long itemId, int quantity) {

        //엔티티 조회
        Member member = memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다. ID: " + memberIdSecretKey));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + itemId));
//        Address address = addressRepository.findAddressesByMember(member).getFirst();
//        Address address = addressRepository.findAddressesByMemberIdSecretKey(member.getId()).get();

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

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Optional<Order> order = orderRepository.findById(orderId);
        //주문 취소
        order.ifPresent(Order::cancel);
    }

    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 정보가 존재하지 않습니다. ID: " + orderId));
    }

}
