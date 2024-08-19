package com.task.management.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String userName;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String password;
}
