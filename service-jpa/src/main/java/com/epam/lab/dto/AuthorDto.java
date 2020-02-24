package com.epam.lab.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class AuthorDto extends AbstractDto {

    @NotNull
    @Pattern(regexp = "[A-ZА-Я]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided name is not valid")
    private String name;
    
    @NotNull
    @Pattern(regexp = "[A-ZА-Я\\-]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided surname is not valid")
    private String surname;

    public AuthorDto() {
        super();
    }

    public AuthorDto(final Long id, final String nameValue, final String surnameValue) {
        super(id);
        name = nameValue;
        surname = surnameValue;
    }

    public String getName() {
        return name;
    }

    public void setName(final String nameValue) {
        name = nameValue;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surnameValue) {
        surname = surnameValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(name, authorDto.name) && Objects.equals(surname, authorDto.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return String.format("AuthorDto{id=%d, name='%s', surname='%s'}", getId(), name, surname);
    }
}
