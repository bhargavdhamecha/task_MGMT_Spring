package com.task.management.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
    private boolean rememberMe;
}
