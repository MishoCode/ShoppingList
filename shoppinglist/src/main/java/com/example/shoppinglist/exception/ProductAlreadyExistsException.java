package com.example.shoppinglist.exception;

public class ProductAlreadyExistsException extends ObjectAlreadyExistsException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
