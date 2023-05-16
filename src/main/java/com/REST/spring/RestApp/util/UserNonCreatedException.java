package com.REST.spring.RestApp.util;

public class UserNonCreatedException extends RuntimeException {
    public UserNonCreatedException(String message) {
        super(message);
    }
}
