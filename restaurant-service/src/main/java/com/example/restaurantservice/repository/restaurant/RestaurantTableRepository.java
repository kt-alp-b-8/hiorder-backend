package com.example.restaurantservice.repository.restaurant;

import com.example.restaurantservice.entity.restaurant.Restaurant;
import com.example.restaurantservice.entity.restaurant.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    // 특정 식당 + 테이블 이름으로 조회
//    Optional<RestaurantTable> findByRestaurant_RestaurantIdAndTableName(Long restaurantId, String tableName);

    // 특정 식당의 테이블 목록, table_id 기준 오름차순
    @Query(value = "SELECT * FROM restaurant_table t " +
            "WHERE t.restaurant_id = :restaurantId " +
            "ORDER BY t.table_id ASC",
            nativeQuery = true)
    List<RestaurantTable> findByRestaurantIdOrderByTableIdAsc(@Param("restaurantId") Long restaurantId);


    Optional<RestaurantTable> findRestaurantTableByRestaurantAndTableName(Restaurant restaurant, String tableName);

//    findByRestaurant_RestaurantIdAndTableName -> 쿼리 만들기
}
