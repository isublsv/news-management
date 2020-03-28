package com.epam.lab.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "\"user\"", schema = "news", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class User extends AbstractEntity implements Pageable {

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "surname", length = 20, nullable = false)
    private String surname;

    @Column(name = "login", length = 30, unique = true, nullable = false)
    private String login;

    @Column(name = "password", length = 30, nullable = false)
    private String password;
    
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "roles",
            schema = "news",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public User() {
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
        User user = (User) o;
        return name.equals(user.name)
               && surname.equals(user.surname)
               && login.equals(user.login)
               && password.equals(user.password)
               && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, login, password, roles);
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', surname='%s', login='%s', password='%s', roles='%s'}",
                             getId(), name, surname, login, password, roles);
    }
}
