package com.task.management.security.controller;

import com.task.management.shared.constant.ApiConstant;
import com.task.management.dto.LoginRequestDTO;
import com.task.management.dto.SignupRequestDTO;
import com.task.management.security.service.AuthService;
import com.task.management.shared.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = ApiConstant.AUTH_CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * API for check username available while creating a new user
     * @param usernameBody map<string, object>
     * @return ApiResponse
     */
    @PostMapping(value = ApiConstant.CHECK_USERNAME)
    public ApiResponse<Object> getRequest(@RequestBody Map<String, Object> usernameBody) {
        String name = (String) usernameBody.get("username");
        if (!name.isEmpty()) {
            return authService.checkUserName(name);
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "bad request!", null);
    }

    /**
     * API for user login
     * @param loginBody LoginRequestDTO
     * @param response HttpServletResponse
     * @return ApiResponse
     */
    @PostMapping(value = ApiConstant.LOGIN)
    public ApiResponse<Object> login(@RequestBody LoginRequestDTO loginBody, HttpServletResponse response){
        return authService.authenticate(loginBody,response);
    }

    /**
     * API for creating new user
     * @param signupBody SignupRequestDTO
     * @return ApiResponse
     */
    @PostMapping(value = ApiConstant.SIGNUP)
    public ApiResponse<Object> signUp(@RequestBody SignupRequestDTO signupBody, HttpServletResponse response){
        return authService.signUp(signupBody, response);
    }

    /**
     * API for logout, clear the session
     * @param response HttpServletResponse
     * @return ApiResponse
     */
    @PostMapping(value = ApiConstant.LOGOUT)
    public ApiResponse<Object> logout(HttpServletResponse response){
        return authService.logout(response);
    }
}
