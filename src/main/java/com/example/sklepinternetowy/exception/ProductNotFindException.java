package com.example.sklepinternetowy.exception;

public class ProductNotFindException extends RuntimeException{
    public ProductNotFindException(String message) {
        super(message);
    }

    public ProductNotFindException(String message, Throwable cause) {
        super(message, cause);
    }
}
