package com.lessonlink.repository.order;

import com.lessonlink.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    Page<Order> findAllWithMemberDelivery(Pageable pageable);
}
