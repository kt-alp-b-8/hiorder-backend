package com.example.restaurantservice.repository.menu;


import com.example.restaurantservice.entity.menu.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    // 식당 ID로 카테고리 목록 찾기
    List<MenuCategory> findByRestaurant_Id(Long restaurantId);

    // 식당 ID로 카테고리 목록 + displayOrder 순으로 정렬
    List<MenuCategory> findByRestaurant_IdOrderByDisplayOrderAsc(Long restaurantId);
}
