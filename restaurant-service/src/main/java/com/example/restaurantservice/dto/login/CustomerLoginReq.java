package com.example.restaurantservice.dto.login;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoginReq {
    private String restaurantName;
    private String tableName;
}
