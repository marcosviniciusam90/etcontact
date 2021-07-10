package com.mvam.etcontact.services.exceptions;

public class DatabaseIntegrityException extends RuntimeException{

    public DatabaseIntegrityException(Long id) {
        super(String.format("Database integrity violation when try to delete resource with ID %s", id));
    }
}
