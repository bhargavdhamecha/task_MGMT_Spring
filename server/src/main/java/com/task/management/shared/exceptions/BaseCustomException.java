package com.task.management.shared.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCustomException extends RuntimeException{

    private static final long serialVersionUID = 5878784447971587131L;
	private boolean sendResponse;

    public BaseCustomException(String message, boolean sendResponse){
        super(message);
        this.sendResponse = sendResponse;
    }
}
