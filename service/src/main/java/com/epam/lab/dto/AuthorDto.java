package com.epam.lab.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AuthorDto extends AbstractDto {

    @NotBlank
    @Length(min = 2, max = 30, message = "Name cannot be null and must be between 2 and 30 characters")
    private String name;
    
    @NotBlank
    @Length(min = 2, max = 30, message = "Surname cannot be null and must be between 2 and 30 characters")
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
