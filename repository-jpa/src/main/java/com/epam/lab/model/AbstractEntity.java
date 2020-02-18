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
    private Long id;

    public AbstractEntity() {
    }

    public AbstractEntity(final Long ldValue) {
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
