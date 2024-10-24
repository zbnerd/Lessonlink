package com.lessonlink.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Setter
    private int orderPrice;

    @Setter
    private int orderQuantity;

    //== 주문생성 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int orderQuantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setOrderQuantity(orderQuantity);
        item.removeStock(orderQuantity);
        return orderItem;
    }
    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(orderQuantity);
    }
    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getOrderQuantity();
    }
}
