package com.epam.lab.exception;

public class EntityDuplicatedException extends RepositoryException {
    private static final String MESSAGE = "Entity with provided name already exists";

    public EntityDuplicatedException() {
        super(MESSAGE);
    }
}
