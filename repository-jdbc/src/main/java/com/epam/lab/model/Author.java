package com.epam.lab.model;

public class Author extends Entity{

    private String name;
    private String surname;

    public Author(long id, String name, String surname) {
        super(id);
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s'}", getId(), name, surname);
    }
}
