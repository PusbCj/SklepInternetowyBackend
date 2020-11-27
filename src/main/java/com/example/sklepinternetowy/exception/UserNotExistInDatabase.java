package com.example.sklepinternetowy.exception;

public class UserNotExistInDatabase extends RuntimeException {
    public UserNotExistInDatabase(String message) {
        super(message);
    }

    public UserNotExistInDatabase(String message, Throwable cause) {
        super(message, cause);
    }
}
