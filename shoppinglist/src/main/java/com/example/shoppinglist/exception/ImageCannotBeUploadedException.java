package com.example.shoppinglist.exception;

public class ImageCannotBeUploadedException extends RuntimeException {
    public ImageCannotBeUploadedException(String message) {
        super(message);
    }
}
