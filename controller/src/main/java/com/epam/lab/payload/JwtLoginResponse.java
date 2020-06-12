package com.epam.lab.payload;

import java.util.List;

public class JwtLoginResponse {
    private String token;
    private String type;
    private Long id;
    private String name;
    private String surname;
    private String login;
    private List<String> roles;

    public JwtLoginResponse(
            final String tokenValue,
            final Long idValue,
            final String nameValue,
            final String surnameValue,
            final String loginValue,
            final List<String> rolesValue) {
        token = tokenValue;
        type = "Bearer";
        id = idValue;
        name = nameValue;
        surname = surnameValue;
        login = loginValue;
        roles = rolesValue;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String tokenValue) {
        token = tokenValue;
    }

    public String getType() {
        return type;
    }

    public void setType(final String typeValue) {
        type = typeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idValue) {
        id = idValue;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(final String loginValue) {
        login = loginValue;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(final List<String> rolesValue) {
        roles = rolesValue;
    }
}
