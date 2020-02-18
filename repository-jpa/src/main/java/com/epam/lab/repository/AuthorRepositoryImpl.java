package com.epam.lab.repository;

import com.epam.lab.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {


    @Override
    public Author findByAuthor(final Author author) {
        return null;
    }

    @Override
    public Author create(final Author entity) {
        return null;
    }

    @Override
    public Author find(final Long id) {
        return null;
    }

    @Override
    public Author update(final Author entity) {
        return null;
    }

    @Override
    public void delete(final Long id) {

    }
}
