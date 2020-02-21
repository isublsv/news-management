package com.epam.lab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "news", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag extends AbstractEntity {

    @Column(name = "name", length = 30, unique = true, nullable = false)
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

    public String getName() {
        return name;
    }

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
