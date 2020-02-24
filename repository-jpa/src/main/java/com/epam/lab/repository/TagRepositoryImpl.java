package com.epam.lab.repository;

import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Tag;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(final Tag entity) {
        try {
            entityManager.persist(entity);
        } catch (ConstraintViolationException e) {
            throw new EntityDuplicatedException();
        }
        return entity;
    }

    @Override
    public Tag find(final Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            return tag;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Tag update(final Tag entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Long id) {
        entityManager.remove(find(id));
    }
}