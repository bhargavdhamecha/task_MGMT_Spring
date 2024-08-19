package com.task.management.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;
    private Date createdAt;
}
