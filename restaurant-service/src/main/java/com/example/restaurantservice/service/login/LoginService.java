package com.example.restaurantservice.service.login;

import com.example.restaurantservice.dto.login.CustomerLoginReq;
import com.example.restaurantservice.dto.login.CustomerLoginRes;
import com.example.restaurantservice.dto.login.OwnerLoginReq;
import com.example.restaurantservice.dto.login.OwnerLoginRes;
import com.example.restaurantservice.entity.restaurant.Restaurant;
import com.example.restaurantservice.entity.restaurant.RestaurantTable;
import com.example.restaurantservice.repository.restaurant.RestaurantRepository;
import com.example.restaurantservice.repository.restaurant.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantTableRepository restaurantTablesRepository;


    public CustomerLoginRes customerLogin(CustomerLoginReq request) {
        // 1) 파라미터 검증
        if (request.getRestaurantName() == null || request.getRestaurantName().isEmpty() ||
                request.getTableName() == null || request.getTableName().isEmpty()) {
            // 직접 예외 던지거나, null 처리
            throw new IllegalArgumentException("레스토랑 이름과 테이블 이름은 필수입니다.");
        }

        // 2) 레스토랑 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(request.getRestaurantName())
                .orElseThrow(() -> new RuntimeException("해당 레스토랑이 존재하지 않습니다."));

        // 3) 테이블 조회
        RestaurantTable restaurantTables = restaurantTablesRepository
                .findRestaurantTableByRestaurantAndTableName(restaurant, request.getTableName())
                .orElseThrow(() -> new RuntimeException("해당 테이블이 존재하지 않습니다."));

        // 4) 정상적으로 찾았으면 응답 생성
        return CustomerLoginRes.builder()
                .status(200)
                .success(true)
                .restaurantId(restaurant.getId())
                .tableId(restaurantTables.getId())
                .build();
    }


    public OwnerLoginRes ownerLogin(OwnerLoginReq request) {
        // 1) 파라미터 검증
        if (request.getRestaurantName() == null || request.getRestaurantName().isEmpty()) {
            throw new IllegalArgumentException("레스토랑 이름은 필수입니다.");
        }

        // 2) 레스토랑 조회
        Restaurant restaurant = restaurantRepository.findByRestaurantName(request.getRestaurantName())
                .orElseThrow(() -> new RuntimeException("해당 레스토랑이 존재하지 않습니다."));

        // 3) 정상적으로 찾았으면 응답 생성
        return OwnerLoginRes.builder()
                .status(200)
                .success(true)
                .restaurantId(restaurant.getId())
                .build();
    }
}

