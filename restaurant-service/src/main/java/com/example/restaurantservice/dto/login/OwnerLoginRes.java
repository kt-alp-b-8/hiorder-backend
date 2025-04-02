
package com.example.restaurantservice.dto.login;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerLoginRes {
    private int status;        // 200
    private boolean success;   // true
    private Long restaurantId; // 사장님 식당 id
    private String message;    // 필요 시
}

