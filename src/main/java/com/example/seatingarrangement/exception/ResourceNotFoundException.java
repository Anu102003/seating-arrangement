package com.example.seatingarrangement.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String error) {
        super(error);
    }
}
