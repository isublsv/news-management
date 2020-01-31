package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    private long id;

    public AbstractDto() {
    }

    public AbstractDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
