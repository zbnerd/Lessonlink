package com.lessonlink.repository;

import com.lessonlink.domain.member.QMember;
import com.lessonlink.domain.order.Order;
import com.lessonlink.domain.order.QOrder;
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

import static com.lessonlink.domain.member.QMember.member;
import static com.lessonlink.domain.order.QOrder.*;


@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Order> findAll(OrderSearch orderSearch, Pageable pageable) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

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

}
