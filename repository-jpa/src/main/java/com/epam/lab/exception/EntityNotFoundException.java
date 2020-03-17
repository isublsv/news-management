package com.epam.lab.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RepositoryException {
    private static final String REASON = "Entity not found";

    public EntityNotFoundException() {
        super(REASON);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
