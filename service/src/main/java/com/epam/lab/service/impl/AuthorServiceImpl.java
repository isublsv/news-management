package com.epam.lab.service.impl;

import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void create(final Author entity) {
        authorRepository.create(entity);
        System.out.println("The Author was added!");
    }

    @Override
    public Author find(final long id) {
        return authorRepository.find(id);
    }

    @Override
    public void update(final Author entity) {
        authorRepository.update(entity);
        System.out.println("The Author was updated! " + entity);
    }

    @Override
    public void delete(final long id) {
        authorRepository.delete(id);
        System.out.println("The Author was deleted by id=" + id);
    }
}
