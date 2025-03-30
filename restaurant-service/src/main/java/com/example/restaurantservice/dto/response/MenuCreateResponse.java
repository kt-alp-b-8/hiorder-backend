package com.example.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCreateResponse {

    private Long menuId;
    private Long menuCategoryId;

    public static MenuCreateResponse of(Long menuId, Long menuCategoryId) {
        return new MenuCreateResponse(menuId, menuCategoryId);
    }
}

