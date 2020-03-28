package com.epam.lab.repository;

import com.epam.lab.model.Role;
import com.epam.lab.model.User;
import com.epam.lab.model.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(final User entity) {
        entity.getRoles().add(Role.USER);
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
        User user = find(entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        return user;
    }

    @Override
    public void delete(final Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.select(from).where(criteriaBuilder.equal(from.get(User_.LOGIN), username));
        return Optional.of(entityManager.createQuery(query).getSingleResult());
    }
}
