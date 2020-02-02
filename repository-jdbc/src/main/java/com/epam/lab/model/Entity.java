package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component
public abstract class Entity {

    private long id;

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
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param ldValue value of id.
     */
    public void setId(final long ldValue) {
        id = ldValue;
    }

    @Override
    public String toString() {
        return String.format("Entity{id=%d}", id);
    }
}
