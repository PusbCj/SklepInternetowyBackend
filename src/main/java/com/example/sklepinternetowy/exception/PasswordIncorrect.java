package com.example.sklepinternetowy.exception;

public class PasswordIncorrect extends RuntimeException{
    public PasswordIncorrect(String message) {
        super(message);
    }

    public PasswordIncorrect(String message, Throwable cause) {
        super(message, cause);
    }
}
