package com.epam.lab.exception;

public class EntityNotFoundException extends RepositoryException {
    private static final String MESSAGE = "Entity not found";

    public EntityNotFoundException() {
        super(MESSAGE);
    }
}
