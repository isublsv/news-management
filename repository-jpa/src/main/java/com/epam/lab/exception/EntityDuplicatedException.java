package com.epam.lab.exception;

import org.springframework.http.HttpStatus;

public class EntityDuplicatedException extends RepositoryException {
    private static final String REASON = "Entity with provided name already exists";

    public EntityDuplicatedException() {
        super(REASON);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
