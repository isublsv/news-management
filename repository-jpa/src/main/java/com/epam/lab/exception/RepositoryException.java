package com.epam.lab.exception;

import org.springframework.http.HttpStatus;

public abstract class RepositoryException extends RuntimeException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();
}
