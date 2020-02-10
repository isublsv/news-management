package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

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
