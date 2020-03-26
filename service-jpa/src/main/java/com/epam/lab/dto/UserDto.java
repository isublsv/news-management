package com.epam.lab.dto;

import com.epam.lab.model.Pageable;
import com.epam.lab.model.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDto extends AbstractDto implements Pageable {

    @NotBlank(message = "Provided user name must not be blank")
    @Length(min = 2, max = 20, message = "The user name length must be between 2 and 20 characters.")
    @Pattern(regexp = "^[A-ZА-Я]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided user name is not valid")
    private String name;

    @NotBlank(message = "Provided user surname must not be blank")
    @Length(min = 2, max = 20, message = "The user surname length must be between 2 and 20 characters.")
    @Pattern(regexp = "^[A-ZА-Я\\-]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided user surname is not valid")
    private String surname;

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

    private Set<Role> roles = new HashSet<>();

    public UserDto() {
        super();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String passwordValue) {
        password = passwordValue;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> rolesValue) {
        roles = rolesValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return name.equals(userDto.name)
               && surname.equals(userDto.surname)
               && login.equals(userDto.login)
               && password.equals(userDto.password)
               && roles.equals(userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, roles);
    }

    @Override
    public String toString() {
        return String.format("UserDto{id=%d, name='%s', surname='%s', login='%s', password='%s', roles='%s'}",
                             getId(), name, surname, login, password, roles);
    }
}
