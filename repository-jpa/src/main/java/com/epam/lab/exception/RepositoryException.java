package com.epam.lab.exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(final String message) {
        super(message);
    }
}
