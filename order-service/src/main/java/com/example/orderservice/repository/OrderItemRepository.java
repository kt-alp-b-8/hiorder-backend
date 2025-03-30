package com.example.orderservice.repository;

import java.util.List;

import com.example.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 주문 ID로 order_items 목록 조회 (Native Query)
    @Query(value = "SELECT * FROM order_item oi WHERE oi.order_id = :orderId",
            nativeQuery = true)
    List<OrderItem> findByOrdersOrderId(@Param("orderId") Long orderId);
}