package com.example.restaurantservice.repository.menu;

import com.example.restaurantservice.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // 식당ID + 카테고리ID로 메뉴 목록
    List<Menu> findByRestaurant_IdAndMenuCategory_Id(Long restaurantId, Long menuCategoryId);

    // 식당ID + 카테고리ID + displayOrder 정렬
    List<Menu> findByRestaurant_IdAndMenuCategory_IdOrderByDisplayOrderAsc(Long restaurantId, Long menuCategoryId);

    @Query(
            value = "SELECT COALESCE(MAX(m.display_order), 0) " +
                    "FROM menu m " +
                    "WHERE m.menu_category_id = :menuCategoryId",
            nativeQuery = true
    )
    Integer findMaxDisplayOrderByCategoryId(@Param("menuCategoryId") Long menuCategoryId);
}

