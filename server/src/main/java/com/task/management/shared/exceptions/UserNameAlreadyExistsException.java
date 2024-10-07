package com.task.management.shared.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = -5679609933185502885L;

	public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
