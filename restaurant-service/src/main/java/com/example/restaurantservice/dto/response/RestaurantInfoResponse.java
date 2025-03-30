package com.example.restaurantservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantInfoResponse {

    private String restaurantName;
    private String tableName;
    // 필요하다면 추가 필드 (ex. message, etc.)
}
