package com.example.restaurantservice.entity.menu;

import com.example.restaurantservice.entity.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu_category")
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_category_id")
    private Long id;

    @Column(name = "menu_category_name", length = 40, nullable = false)
    private String menuCategoryName;

    @Column(name = "menu_category_name_en", length = 40)
    private String menuCategoryNameEn;

    @Column(name = "menu_category_name_zh", length = 40)
    private String menuCategoryNameZh;

    @Column(name = "menu_category_name_jp", length = 40)
    private String menuCategoryNameJp;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    private MenuCategory(String menuCategoryName, String menuCategoryNameEn, String menuCategoryNameZh,
                        String menuCategoryNameJp, int displayOrder, Restaurant restaurant) {
        this.menuCategoryName = menuCategoryName;
        this.menuCategoryNameEn = menuCategoryNameEn;
        this.menuCategoryNameZh = menuCategoryNameZh;
        this.menuCategoryNameJp = menuCategoryNameJp;
        this.displayOrder = displayOrder;
        this.restaurant = restaurant;
    }

    //== 비지니스 로직 ==//

    /**
     * 한국어 메뉴 카테고리 저장
     * @param name
     */
    public void updateMenuCategoryName(String name) {
        this.menuCategoryName = name;
    }
    /**
     * 영어 메뉴 카테고리 저장
     * @param name
     */
    public void updateMenuCategoryNameEn(String name) {
        this.menuCategoryNameEn = name;
    }

    /**
     * 중국어 메뉴 카테고리 저장
     * @param name
     */
    public void updateMenuCategoryNameZh(String name) {
        this.menuCategoryNameZh = name;
    }

    /**
     * 일본어 메뉴 카테고리 저장
     * @param name
     */
    public void updateMenuCategoryNameJp(String name) {
        this.menuCategoryNameJp = name;
    }
}

