package com.epam.lab.service;

import com.epam.lab.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends Service<UserDto>, UserDetailsService {
}
