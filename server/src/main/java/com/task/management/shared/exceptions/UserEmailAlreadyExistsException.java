package com.task.management.shared.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException{
    public UserEmailAlreadyExistsException(String message){
        super(message);
    }
}
