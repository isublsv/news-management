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
    public AuthorServiceImpl(final AuthorRepository authorRepositoryValue, final AuthorMapper authorMapperValue) {
        this.authorRepository = authorRepositoryValue;
        this.authorMapper = authorMapperValue;
    }

    @Override
    public AuthorDto create(final AuthorDto entityDto) {
        return authorMapper.toDto(authorRepository.create(authorMapper.toEntity(entityDto)));
    }

    @Override
    public AuthorDto find(final long id) {
        return authorMapper.toDto(authorRepository.find(id));
    }

    @Override
    public void update(final AuthorDto entityDto) {
        authorRepository.update(authorMapper.toEntity(entityDto));
    }

    @Override
    public void delete(final long id) {
        authorRepository.delete(id);
    }
}
