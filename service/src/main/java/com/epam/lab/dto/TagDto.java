package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("tagDto")
public class TagDto extends AbstractDto {

    private String name;

    public TagDto() {
        super();
    }

    public TagDto(final String nameValue) {
        super();
        name = nameValue;
    }

    public TagDto(final Long idValue, final String nameValue) {
        super(idValue);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("TagDto{id=%d, name='%s'}", getId(), name);
    }
}
