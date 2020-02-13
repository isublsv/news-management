package com.epam.lab.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TagDto extends AbstractDto {

    @NotNull
    @Length(min = 2, max = 30, message = "Name cannot be null and must be between 2 and 30 characters")
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

    public String getName() {
        return name;
    }

    public void setName(final String nameValue) {
        name = nameValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
