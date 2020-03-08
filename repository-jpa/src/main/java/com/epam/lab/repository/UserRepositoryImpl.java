package com.epam.lab.repository;

import com.epam.lab.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(final User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public User find(final Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            return user;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User update(final User entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Long id) {
        entityManager.remove(find(id));
    }
}
