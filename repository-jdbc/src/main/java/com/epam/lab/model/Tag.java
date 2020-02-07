package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("tag")
public class Tag extends Entity {
    
    private String name;

    public Tag() {
        super();
    }

    public Tag(final Long idValue) {
        super(idValue);
    }

    public Tag(final String nameValue) {
        super();
        name = nameValue;
    }

    public Tag(final Long id, final String nameValue) {
        super(id);
        name = nameValue;
    }

    /**
     * Gets name.
     *
     * @return value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param nameValue value of name.
     */
    public void setName(final String nameValue) {
        name = nameValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public String toString() {
        return String.format("Tag{id=%d, name='%s'}", getId(), name);
    }
}
