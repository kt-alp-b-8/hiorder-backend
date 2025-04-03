package com.example.restaurantservice.controller.login;



import com.example.restaurantservice.controller.api.ApiResult;
import com.example.restaurantservice.dto.login.CustomerLoginReq;
import com.example.restaurantservice.dto.login.CustomerLoginRes;
import com.example.restaurantservice.dto.login.OwnerLoginReq;
import com.example.restaurantservice.dto.login.OwnerLoginRes;
import com.example.restaurantservice.exception.CustomException;
import com.example.restaurantservice.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.restaurantservice.exception.ErrorCode.LOGIN_NO_PARAMETER;
import static com.example.restaurantservice.exception.ErrorCode.RESTAURANT_NOT_FOUND;

@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "https://polite-pond-0844fed00.6.azurestaticapps.net")
@RestController
@RequestMapping("/restaurant/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/customer")
    public ApiResult<?> customerLogin(@RequestBody CustomerLoginReq request) {
        CustomerLoginRes response = loginService.customerLogin(request);
        return ApiResult.ok(HttpStatus.OK, response);
    }

    @PostMapping("/owner")
    public ApiResult<?> ownerLogin(@RequestBody OwnerLoginReq request) {
        OwnerLoginRes response = loginService.ownerLogin(request);
        return ApiResult.ok(HttpStatus.OK, response);
    }




}
