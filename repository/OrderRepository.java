package com.lessonlink.repository;

import com.lessonlink.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Query("select o from Order o join fetch o.orderItems oi where o.id = :orderId")
    List<Order> findOrdersByOrderId(Long orderId);

    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    List<Order> findAllWithMemberDelivery();

    @Query("select distinct o from Order o" +
            " join fetch o.member m" +
            " join fetch o.delivery d" +
            " join fetch o.orderItems oi" +
            " join fetch oi.item i")
    List<Order> findAllWithItem();

    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    Page<Order> findAllWithMemberDelivery(Pageable pageable);
}
