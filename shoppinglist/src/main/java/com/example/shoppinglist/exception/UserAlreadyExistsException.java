package com.example.shoppinglist.exception;

public class UserAlreadyExistsException extends ObjectAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
