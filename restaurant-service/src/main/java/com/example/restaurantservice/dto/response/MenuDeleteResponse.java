package com.example.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDeleteResponse {

    private Long menuId;

    public static MenuDeleteResponse of(Long menuId) {
        return new MenuDeleteResponse(menuId);
    }
}