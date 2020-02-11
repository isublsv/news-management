package com.epam.lab.dto;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    @Range(min = 1L, message = "Id cannot be less 1 and more than Long.MAX_VALUE")
    private Long id;

    public AbstractDto() {
    }

    public AbstractDto(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Gets id.
     *
     * @return value of id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param idValue value of id.
     */
    public void setId(final Long idValue) {
        id = idValue;
    }
}
