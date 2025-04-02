package com.example.restaurantservice.dto.login;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoginRes {
    private int status;
    private boolean success;
    private Long restaurantId;
    private Long tableId;
    private String message; // 필요 시
}

