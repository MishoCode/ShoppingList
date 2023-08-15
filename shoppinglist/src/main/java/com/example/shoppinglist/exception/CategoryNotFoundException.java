package com.example.shoppinglist.exception;

public class CategoryNotFoundException extends ObjectNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
