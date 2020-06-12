package com.epam.lab.payload;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginRequest {

    @NotBlank(message = "Provided user login must not be blank")
    @Length(min = 2, max = 30, message = "The user login length must be between 2 and 30 characters.")
    @Pattern(regexp = "^[A-ZА-Я_\\-\\d]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided user login is not valid")
    private String login;

    @NotBlank(message = "Provided user password must not be blank")
    @Length(min = 8, max = 30, message = "The user password length must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{8,30}$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided user password is not valid")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String loginValue) {
        login = loginValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String passwordValue) {
        password = passwordValue;
    }
}
