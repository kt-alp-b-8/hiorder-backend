package com.example.orderservice.dto.response;

import com.example.orderservice.dto.OrderInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOrderHistoryResponse {

    private List<OrderInfoDto> data;

    public static RestaurantOrderHistoryResponse of(List<OrderInfoDto> data) {
        return new RestaurantOrderHistoryResponse(data);
    }
}
