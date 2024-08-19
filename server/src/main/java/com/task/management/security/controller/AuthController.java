package com.task.management.security.controller;

import com.task.management.constant.ApiConstant;
import com.task.management.dto.LoginRequestDTO;
import com.task.management.dto.ResponseStructure;
import com.task.management.dto.SignupRequestDTO;
import com.task.management.security.service.AuthService;
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

    @PostMapping(value = ApiConstant.CHECK_USERNAME)
    public ResponseStructure<Object> getRequest(@RequestBody Map<String, Object> usernameBody) {
        String name = (String) usernameBody.get("username");
        if (!name.isEmpty()) {
            return authService.checkUserName(name);
        }
        return new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(), "bad request!", null);
    }

    @PostMapping(value = ApiConstant.LOGIN)
    public ResponseStructure<Object> login(@RequestBody LoginRequestDTO loginBody, HttpServletResponse response){
        return authService.authenticate(loginBody,response);
    }

    @PostMapping(value = ApiConstant.SIGNUP)
    public ResponseStructure<Object> signUp(@RequestBody SignupRequestDTO signupBody){
        return authService.signUp(signupBody);
    }

    @PostMapping(value = ApiConstant.LOGOUT)
    public ResponseStructure<Object> logout(HttpServletResponse response){
        return authService.logout(response);
    }
}
