package com.rocketteam.auth.web.exceptions;

public class JWTException extends RuntimeException {
    public JWTException(String message) {
        super(message);
    }
}
