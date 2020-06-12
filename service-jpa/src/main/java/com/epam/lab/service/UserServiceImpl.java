package com.epam.lab.service;

import com.epam.lab.dto.UserDto;
import com.epam.lab.dto.UserMapper;
import com.epam.lab.model.User;
import com.epam.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepositoryValue, final UserMapper userMapperValue) {
        userRepository = userRepositoryValue;
        userMapper = userMapperValue;
    }

    @Transactional
    @Override
    public UserDto create(final UserDto entityDto) {
        User user = userMapper.toEntity(entityDto);
        return userMapper.toDto(userRepository.create(user));
    }

    @Override
    public UserDto find(final Long id) {
        return userMapper.toDto(userRepository.find(id));
    }

    @Transactional
    @Override
    public UserDto update(final UserDto entityDto) {
        User user = userRepository.update(userMapper.toEntity(entityDto));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        userRepository.delete(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
