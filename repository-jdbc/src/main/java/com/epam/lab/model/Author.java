package com.epam.lab.model;

import java.util.Objects;

public class Author extends Entity {

    private String name;
    private String surname;

    public Author() {
        super();
    }

    public Author(final String nameValue, final String surnameValue) {
        super();
        name = nameValue;
        surname = surnameValue;
    }

    public Author(final Long id, final String nameValue, final String surnameValue) {
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
    public boolean equals(Object oValue) {
        if (this == oValue) return true;
        if (oValue == null || getClass() != oValue.getClass()) return false;
        Author author = (Author) oValue;
        return Objects.equals(name, author.name) &&
                Objects.equals(surname, author.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s'}", getId(), name, surname);
    }
}
