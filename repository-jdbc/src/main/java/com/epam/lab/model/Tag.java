package com.epam.lab.model;

public class Tag extends Entity {
    
    private String name;

    public Tag(final String nameValue) {
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
