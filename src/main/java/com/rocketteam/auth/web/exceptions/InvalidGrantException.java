package com.rocketteam.auth.web.exceptions;

public class InvalidGrantException extends RuntimeException {
    public InvalidGrantException(String message) {
        super(message);
    }
}
