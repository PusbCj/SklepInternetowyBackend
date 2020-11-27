package com.example.sklepinternetowy.exception;

public class PasswordKeyIsInvalid extends RuntimeException{
    public PasswordKeyIsInvalid(String message) {
        super(message);
    }

    public PasswordKeyIsInvalid(String message, Throwable cause) {
        super(message, cause);
    }
}
