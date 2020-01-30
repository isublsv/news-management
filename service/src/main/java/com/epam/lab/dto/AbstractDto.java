package com.epam.lab.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {

    private Long id;

    public AbstractDto() {
    }

    public AbstractDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
