package com.example.sklepinternetowy.exception;

public class ActivationKeyIsInvalid extends RuntimeException{
    public ActivationKeyIsInvalid(String message) {
        super(message);
    }

    public ActivationKeyIsInvalid(String message, Throwable cause) {
        super(message, cause);
    }
}
