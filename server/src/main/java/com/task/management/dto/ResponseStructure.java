package com.task.management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor()
@AllArgsConstructor()
@Setter()
@Getter()
public class ResponseStructure <T>{
    private int statusCode;
    private String message;
    private T body;

    @Override
    public String toString() {
        return String.format("{\"statusCode\":\"%s\", \"message\":\"%s\", \"body\":%s}",
                statusCode, message, body);
    }
}

