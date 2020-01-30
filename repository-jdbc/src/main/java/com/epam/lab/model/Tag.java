package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component("news")
public class Tag extends Entity {
    
    private String name;

    public Tag() {
        super();
    }

    public Tag(String nameValue) {
        super();
        name = nameValue;
    }

    public Tag(final long id, final String nameValue) {
        super(id);
        name = nameValue;
    }

    public String getName() {
        return name;
    }

    public void setName(final String nameValue) {
        name = nameValue;
    }

    @Override
    public String toString() {
        return String.format("Tag{id=%d, name='%s'}", getId(), name);
    }
}
