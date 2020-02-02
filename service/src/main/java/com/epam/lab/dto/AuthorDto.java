package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("authorDto")
public class AuthorDto extends AbstractDto {

    private String name;
    private String surname;
    private List<NewsDto> newsDtos;

    public AuthorDto() {
        super();
    }

    public AuthorDto(final String nameValue, final String surnameValue, final List<NewsDto> newsDtosValue) {
        super();
        name = nameValue;
        surname = surnameValue;
        newsDtos = newsDtosValue;
    }

    public AuthorDto(final long id, final String nameValue, final String surnameValue,
            final List<NewsDto> newsDtosValue) {
        super(id);
        name = nameValue;
        surname = surnameValue;
        newsDtos = newsDtosValue;
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

    /**
     * Gets surname.
     *
     * @return value of surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surnameValue value of surname.
     */
    public void setSurname(final String surnameValue) {
        surname = surnameValue;
    }

    /**
     * Gets newsDtos.
     *
     * @return value of newsDtos.
     */
    public List<NewsDto> getNewsDtos() {
        return newsDtos;
    }

    /**
     * Sets newsDtos.
     *
     * @param newsDtosValue value of newsDtos.
     */
    public void setNewsDtos(final List<NewsDto> newsDtosValue) {
        newsDtos = newsDtosValue;
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
        return Objects.equals(name, authorDto.name) && Objects.equals(surname, authorDto.surname) && newsDtos.equals(
                authorDto.newsDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, newsDtos);
    }
}
