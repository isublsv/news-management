package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.mapper.AuthorMapper;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public AuthorDto create(AuthorDto entityDto) {
        authorRepository.create(authorMapper.toEntity(entityDto));
        System.out.println("The Author was added!");
        return entityDto;
    }

    @Override
    public AuthorDto find(long id) {
        return authorMapper.toDto(authorRepository.find(id));
    }

    @Override
    public void update(AuthorDto entityDto) {
        authorRepository.update(authorMapper.toEntity(entityDto));
        System.out.println("The Author was updated! " + entityDto);
    }

    @Override
    public void delete(long id) {
        authorRepository.delete(id);
        System.out.println("The Author was deleted by id=" + id);
    }
}
