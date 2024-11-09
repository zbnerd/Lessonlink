package com.lessonlink.repository;

import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.condition.OrderSearch;
import com.lessonlink.domain.order.enums.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.lessonlink.domain.delivery.QDelivery.*;
import static com.lessonlink.domain.item.QItem.item;
import static com.lessonlink.domain.member.QMember.*;
import static com.lessonlink.domain.order.QOrder.*;
import static com.lessonlink.domain.order.QOrderItem.orderItem;


@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Order> findAll(OrderSearch orderSearch, Pageable pageable) {

        List<Order> orders = query
                .selectFrom(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()),
                        nameLike(orderSearch.getMemberName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(orders, pageable, orders.size());

    }

    private BooleanExpression statusEq(OrderStatus status) {
        if (status == null) return null;
        return order.status.eq(status);
    }

    private BooleanExpression nameLike(String nameLike) {
        if (!StringUtils.hasText(nameLike)) return null;
        return member.name.like("%" + nameLike + "%");
    }

    public List<Order> findAllWithMemberDelivery() {
        return query
                .selectFrom(order)
                .join(order.delivery, delivery).fetchJoin()
                .fetch();
    }

    public Order findOrderDeliveryByOrderId(Long orderId) {
        return query
                .selectFrom(order)
                .join(order.delivery, delivery).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne();
    }

    public List<Order> findOrdersByOrderId(Long orderId) {
        return query
                .selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .where(order.id.eq(orderId))
                .fetch();
    }

    public List<Order> findAllWithItem() {
        return query
                .selectFrom(order).distinct()
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .fetch();
    }

}
