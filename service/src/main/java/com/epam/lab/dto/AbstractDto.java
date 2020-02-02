package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    private long id;

    public AbstractDto() {
    }

    public AbstractDto(final long idValue) {
        this.id = idValue;
    }

    /**
     * Gets id.
     *
     * @return value of id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param idValue value of id.
     */
    public void setId(final long idValue) {
        id = idValue;
    }
}
