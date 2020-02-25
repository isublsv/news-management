package com.epam.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Entity with provided name already exists")
public class EntityDuplicatedException extends RepositoryException {

    public EntityDuplicatedException() {
        super();
    }
}
