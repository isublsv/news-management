package com.epam.lab.service;

import com.epam.lab.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    
    private Long id;
    private String name;
    private String surname;
    private String login;

    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            final Long idValue,
            final String nameValue,
            final String surnameValue,
            final String loginValue,
            final String passwordValue,
            final Collection<? extends GrantedAuthority> authoritiesValue) {
        id = idValue;
        name = nameValue;
        surname = surnameValue;
        login = loginValue;
        password = passwordValue;
        authorities = authoritiesValue;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                                                 .stream()
                                                 .map(role -> new SimpleGrantedAuthority(role.name()))
                                                 .collect(Collectors.toList());
        
        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getLogin(),
                user.getPassword(),
                authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String getUsername() {
        return login;
    }
    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl that = (UserDetailsImpl) o;
        return id.equals(that.id)
               && login.equals(that.login)
               && name.equals(that.name)
               && surname.equals(that.surname)
               && password.equals(that.password)
               && authorities.equals(that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, password, authorities);
    }
}
