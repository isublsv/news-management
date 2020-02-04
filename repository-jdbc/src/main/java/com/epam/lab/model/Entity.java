package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component
public abstract class Entity {

    private Long id;

    public Entity() {
    }

    public Entity(final long ldValue) {
        id = ldValue;
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
     * @param ldValue value of id.
     */
    public void setId(final Long ldValue) {
        id = ldValue;
    }

    @Override
    public String toString() {
        return String.format("Entity{id=%d}", id);
    }
}
