package com.epam.lab.exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException() {
    }

    public RepositoryException(final String message) {
        super(message);
    }

    public RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(final Throwable cause) {
        super(cause);
    }
}
