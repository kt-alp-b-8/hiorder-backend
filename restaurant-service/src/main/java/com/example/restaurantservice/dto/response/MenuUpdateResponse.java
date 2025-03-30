package com.example.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuUpdateResponse {

    private Long menuId;
    private Long menuCategoryId;
    private String menuName;        // 수정 후 최종 한글 메뉴명
    private String menuDescription; // 수정 후 최종 한글 메뉴 설명
    private String menuImageUrl;
    private Long price;

    public static MenuUpdateResponse of(Long menuId, Long menuCategoryId,
                                        String menuName, String menuDescription, String menuImageUrl) {
        return MenuUpdateResponse.builder()
                .menuId(menuId)
                .menuCategoryId(menuCategoryId)
                .menuName(menuName)
                .menuDescription(menuDescription)
                .menuImageUrl(menuImageUrl)
                .build();
    }
}
