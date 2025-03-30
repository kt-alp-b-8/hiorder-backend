package com.example.restaurantservice.repository.restaurant;

import com.example.restaurantservice.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 식당 이름으로 식당 조회
    Optional<Restaurant> findByRestaurantName(String restaurantName);
}

