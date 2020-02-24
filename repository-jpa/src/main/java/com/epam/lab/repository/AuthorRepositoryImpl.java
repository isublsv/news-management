package com.epam.lab.repository;

import com.epam.lab.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author create(final Author entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Author find(final Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author update(final Author entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Long id) {
        Author author = find(id);
        if (author != null) {
            entityManager.remove(author);
        }
    }
}
