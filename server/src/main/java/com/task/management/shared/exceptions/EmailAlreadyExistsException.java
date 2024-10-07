package com.task.management.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -6343902317002069794L;

	public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

