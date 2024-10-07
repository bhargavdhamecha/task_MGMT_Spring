package com.task.management.shared.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = -3288911770551290564L;

	public UserEmailAlreadyExistsException(String message){
        super(message);
    }
}
