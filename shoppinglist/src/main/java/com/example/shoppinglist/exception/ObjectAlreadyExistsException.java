package com.example.shoppinglist.exception;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
