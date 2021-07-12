package com.mvam.etcontact.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("Specific resource not found");
    }

    public ResourceNotFoundException(Long id) {
        super(String.format("Resource with ID %s not found", id));
    }
}
