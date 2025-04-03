package com.example.restaurantservice.entity.menu;

import com.example.restaurantservice.entity.BaseEntity;
import com.example.restaurantservice.entity.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    @Column(name = "menu_description", length = 200)
    private String menuDescription;

    // 다국어 컬럼 (영어)
    @Column(name = "menu_name_en", length = 100)
    private String menuNameEn;

    @Column(name = "menu_description_en", length = 200)
    private String menuDescriptionEn;

    // 다국어 컬럼 (중국어)
    @Column(name = "menu_name_zh", length = 100)
    private String menuNameZh;

    @Column(name = "menu_description_zh", length = 200)
    private String menuDescriptionZh;

    // 다국어 컬럼 (일본어)
    @Column(name = "menu_name_jp", length = 100)
    private String menuNameJp;

    @Column(name = "menu_description_jp", length = 200)
    private String menuDescriptionJp;

    @Column(name = "price", nullable = false)
    private int price;  // numeric(10,0) → long or BigDecimal

    @Column(name = "menu_image_url", columnDefinition = "text")
    private String menuImageUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @Builder
    private Menu(String menuName, String menuDescription, String menuNameEn, String menuDescriptionEn,
                String menuNameZh, String menuDescriptionZh, String menuNameJp, String menuDescriptionJp,
                int price, String menuImageUrl, Integer displayOrder, Restaurant restaurant, MenuCategory menuCategory) {

        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.menuNameEn = menuNameEn;
        this.menuDescriptionEn = menuDescriptionEn;
        this.menuNameZh = menuNameZh;
        this.menuDescriptionZh = menuDescriptionZh;
        this.menuNameJp = menuNameJp;
        this.menuDescriptionJp = menuDescriptionJp;
        this.price = price;
        this.menuImageUrl = menuImageUrl;
        this.displayOrder = displayOrder;
        this.restaurant = restaurant;
        this.menuCategory = menuCategory;
    }

    //== 비지니스 로직 ==//

    /**
     * 메뉴명 변경
     * @param menuName
     */
    public void changeMenuName(String menuName){
        this.menuName = menuName;
    }

    /**
     * 메뉴 설명 변경
     * @param menuDescription
     */
    public void changeMenuDescription(String menuDescription){
        this.menuDescription = menuDescription;
    }

    /**
     * 메뉴 영어 버전 저장
     * @param name
     * @param description
     */
    public void updateEnglishVersion(String name, String description) {
        this.menuNameEn = name;
        this.menuDescriptionEn = description;
    }
    /**
     * 메뉴 중국어 버전 저장
     * @param name
     * @param description
     */
    public void updateChineseVersion(String name, String description) {
        this.menuNameZh = name;
        this.menuDescriptionZh = description;
    }
    /**
     * 메뉴 일본어 버전 저장
     * @param name
     * @param description
     */
    public void updateJapaneseVersion(String name, String description) {
        this.menuNameJp = name;
        this.menuDescriptionJp = description;
    }
    /**
     * 메뉴 이름 영어 버전 저장
     * @param name
     */
    public void updateEnglishVersionName(String name) {
        this.menuNameEn = name;
    }
    /**
     * 메뉴 이름 중국어 버전 저장
     * @param name
     */
    public void updateChineseVersionName(String name) {
        this.menuNameZh = name;
    }
    /**
     * 메뉴 일본어 버전 저장
     * @param name
     */
    public void updateJapaneseVersionName(String name) {
        this.menuNameJp = name;
    }
    /**
     * 메뉴 영어 설명 저장
     * @param description
     */
    public void updateEnglishVersionDescription(String description) {
        this.menuDescriptionEn = description;
    }
    /**
     * 메뉴 중국어 설명 저장
     * @param description
     */
    public void updateChineseVersionDescription(String description) {
        this.menuDescriptionZh = description;
    }
    /**
     * 메뉴 일본어 설명 저장
     * @param description
     */
    public void updateJapaneseVersionDescription(String description) {
        this.menuDescriptionJp = description;
    }

    /**
     * 메뉴 가격 변경
     * @param price
     */
    public void updatePrice(int price) {
        this.price = price;
    }

    /**
     * 메뉴 이미지 경로 변경
     * @param menuImageUrl
     */
    public void updateMenuImageUrl(String menuImageUrl) {
        this.menuImageUrl = menuImageUrl;
    }

    /**
     * 메뉴 순서 저장
     * @param order
     */
    public void setDisplayOrder(int order) {
        this.displayOrder = order;
    }
}
