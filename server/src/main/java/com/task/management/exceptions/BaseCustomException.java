package com.task.management.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCustomException extends RuntimeException{

    private boolean sendResponse;

    public BaseCustomException(String message, boolean sendResponse){
        super(message);
        this.sendResponse = sendResponse;
    }
}
