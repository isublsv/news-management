package com.epam.lab.repository;

import com.epam.lab.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(final Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tag find(final Long id) {
        return entityManager.find(Tag.class, id);
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
