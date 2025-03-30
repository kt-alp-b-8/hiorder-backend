package com.example.restaurantservice.dto.response;

import com.example.restaurantservice.dto.TableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTableInfoResponse {

    private List<TableDto> data;   // 테이블 목록

}