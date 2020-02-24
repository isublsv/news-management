package com.epam.lab.dto;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    @PositiveOrZero(message = "Id must be positive")
    private long id;

    public AbstractDto() {
    }

    public AbstractDto(final long idValue) {
        this.id = idValue;
    }

    public long getId() {
        return id;
    }

    public void setId(final long idValue) {
        id = idValue;
    }
}
