package com.epam.lab.dto;

import com.epam.lab.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {
    
    public UserMapper() {
        super(User.class, UserDto.class);
    }
}
