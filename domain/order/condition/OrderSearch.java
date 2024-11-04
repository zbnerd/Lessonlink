package com.lessonlink.domain.order.condition;

import com.lessonlink.domain.order.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {
    private String memberName; //회원 이름
    private OrderStatus orderStatus;//주문 상태[ORDER, CANCEL]

    public OrderSearch() {
    }

    public OrderSearch(String memberName) {
        this(memberName, null);
    }

    public OrderSearch(OrderStatus orderStatus) {
        this(null, orderStatus);
    }

    public OrderSearch(String memberName, OrderStatus orderStatus) {
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }
}
