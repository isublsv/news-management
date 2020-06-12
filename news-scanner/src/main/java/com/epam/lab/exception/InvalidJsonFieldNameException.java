package com.epam.lab.exception;

public class InvalidJsonFieldNameException extends RuntimeException {

    public InvalidJsonFieldNameException(final String message) {
        super(message);
    }
}
