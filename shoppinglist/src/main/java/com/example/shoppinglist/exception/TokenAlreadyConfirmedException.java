package com.example.shoppinglist.exception;

public class TokenAlreadyConfirmedException extends RuntimeException {
    public TokenAlreadyConfirmedException(String message) {
        super(message);
    }
}
