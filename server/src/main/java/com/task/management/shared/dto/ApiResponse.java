package com.task.management.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor()
@Setter()
@Getter()
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T body;

    /**
     * method to convert response from object to string
     * @return string
     */
    @Override
    public String toString() {
        return String.format("{\"statusCode\":\"%s\", \"message\":\"%s\", \"body\":%s}",
                statusCode, message, body);
    }
}


