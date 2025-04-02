package com.example.orderservice.repository;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 레스토랑별 + 당일 기준, orderCode의 최대값 구하기
    // 예: "SELECT coalesce(MAX(o.orderCode), 0) FROM Order o
    //      WHERE o.restaurant.id = :restaurantId
    //        AND cast(o.createdAt as date) = :today"
    @Query(value = "SELECT coalesce(MAX(o.order_code), 0) " +
            "FROM orders o " +
            "WHERE o.restaurant_id = :restaurantId " +
            "  AND CAST(o.created_at AS date) = :today",
            nativeQuery = true)
    int findMaxOrderCodeToday(@Param("restaurantId") Long restaurantId,
                              @Param("today") LocalDate today);


//    // DESC
//    @Query(value = "SELECT * FROM Order o " +
//            "WHERE o.restaurant_id = :restaurantId " +
//            "  AND o.table_id = :tableId " +
//            "  AND o.order_status = :orderStatus " +
//            "ORDER BY o.order_code DESC",
//            nativeQuery = true)
//    List<Order> findOrderByStatusDesc(@Param("restaurantId") Long restaurantId,
//                                        @Param("tableId") Long tableId,
//                                        @Param("Ordertatus") String orderStatus);
//
//    // ASC
//    @Query(value = "SELECT * FROM Order o " +
//            "WHERE o.restaurant_id = :restaurantId " +
//            "  AND o.table_id = :tableId " +
//            "  AND o.order_status = :orderStatus " +
//            "ORDER BY o.order_code ASC",
//            nativeQuery = true)
//    List<Order> findOrderByStatusAsc(@Param("restaurantId") Long restaurantId,
//                                       @Param("tableId") Long tableId,
//                                       @Param("Ordertatus") String orderStatus);
//
//
//    // DESC: 식당 전체 주문 + 특정 상태
//    @Query(value = "SELECT * FROM Order o " +
//            "WHERE o.restaurant_id = :restaurantId " +
//            "  AND o.order_status = :orderStatus " +
//            "ORDER BY o.order_code DESC",
//            nativeQuery = true)
//    List<Order> findOrderByRestaurantStatusDesc(@Param("restaurantId") Long restaurantId,
//                                                  @Param("Ordertatus") String orderStatus);
//
//    // ASC: 식당 전체 주문 + 특정 상태
//    @Query(value = "SELECT * FROM Order o " +
//            "WHERE o.restaurant_id = :restaurantId " +
//            "  AND o.order_status = :orderStatus " +
//            "ORDER BY o.order_code ASC",
//            nativeQuery = true)
//    List<Order> findOrderByRestaurantStatusAsc(@Param("restaurantId") Long restaurantId,
//                                                 @Param("Ordertatus") String orderStatus);

    // DESC
    @Query("SELECT o FROM Order o " +
            "WHERE o.restaurantId = :restaurantId " +
            "AND o.tableId = :tableId " +
            "AND o.orderStatus = :orderStatus " +
            "ORDER BY o.orderCode DESC")
    List<Order> findOrderByStatusDesc(@Param("restaurantId") Long restaurantId,
                                      @Param("tableId") Long tableId,
                                      @Param("orderStatus") OrderStatus orderStatus);

    // ASC
    @Query("SELECT o FROM Order o " +
            "WHERE o.restaurantId = :restaurantId " +
            "AND o.tableId = :tableId " +
            "AND o.orderStatus = :orderStatus " +
            "ORDER BY o.orderCode ASC")
    List<Order> findOrderByStatusAsc(@Param("restaurantId") Long restaurantId,
                                     @Param("tableId") Long tableId,
                                     @Param("orderStatus") OrderStatus orderStatus);

    // DESC: 식당 전체 주문 + 특정 상태
    @Query("SELECT o FROM Order o " +
            "WHERE o.restaurantId = :restaurantId " +
            "AND o.orderStatus = :orderStatus " +
            "ORDER BY o.orderCode DESC")
    List<Order> findOrderByRestaurantStatusDesc(@Param("restaurantId") Long restaurantId,
                                                @Param("orderStatus") OrderStatus orderStatus);

    // ASC: 식당 전체 주문 + 특정 상태
    @Query("SELECT o FROM Order o " +
            "WHERE o.restaurantId = :restaurantId " +
            "AND o.orderStatus = :orderStatus " +
            "ORDER BY o.orderCode ASC")
    List<Order> findOrderByRestaurantStatusAsc(@Param("restaurantId") Long restaurantId,
                                               @Param("orderStatus") OrderStatus orderStatus);


    // 특정 테이블의 주문 상태 변경
    // 특정 식당 + 특정 테이블 + 특정 상태의 주문 목록 조회 (Native Query)
//    @Query(value = "SELECT * FROM Order o " +
//            "WHERE o.restaurant_id = :restaurantId " +
//            "  AND o.table_id = :tableId " +
//            "  AND o.order_status = :orderStatus",
//            nativeQuery = true)
//    List<Order> findAllByRestaurantAndTableAndStatus(@Param("restaurantId") Long restaurantId,
//                                                      @Param("tableId") Long tableId,
//                                                      @Param("orderStatus") String orderStatus);

    @Query("SELECT o FROM Order o " +
            "WHERE o.restaurantId = :restaurantId " +
            "AND o.tableId = :tableId " +
            "AND o.orderStatus = :orderStatus")
    List<Order> findAllByRestaurantAndTableAndStatus(@Param("restaurantId") Long restaurantId,
                                                     @Param("tableId") Long tableId,
                                                     @Param("orderStatus") OrderStatus orderStatus);
}
