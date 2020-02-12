package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component
public abstract class Entity {

    private Long id;

    public Entity() {
    }

    public Entity(final Long ldValue) {
        id = ldValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long ldValue) {
        id = ldValue;
    }

    @Override
    public String toString() {
        return String.format("Entity{id=%d}", id);
    }
}
