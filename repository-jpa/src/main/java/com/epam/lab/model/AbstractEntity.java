package com.epam.lab.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public AbstractEntity() {
    }

    public AbstractEntity(final long ldValue) {
        id = ldValue;
    }

    public long getId() {
        return id;
    }

    public void setId(final long ldValue) {
        id = ldValue;
    }

    @Override
    public String toString() {
        return String.format("Entity{id=%d}", id);
    }
}
