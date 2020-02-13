package com.epam.lab.model;

import org.springframework.stereotype.Component;

@Component("user")
public class User extends Entity{

    private String name;
    private String surname;
    private String login;
    private String password;

    public User() {
        super();
    }

    public User(String name, String surname, String login, String password) {
        super();
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public User(long id, String name, String surname, String login, String password) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', surname='%s', login='%s', password='%s'}", getId(), name, surname, login, password);
    }
}
