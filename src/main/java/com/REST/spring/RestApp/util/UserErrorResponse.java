package com.REST.spring.RestApp.util;

import lombok.Data;

@Data
public class UserErrorResponse {
private String message;
private long timestamp;

    public UserErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

