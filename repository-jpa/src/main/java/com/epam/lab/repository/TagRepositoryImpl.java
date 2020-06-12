package com.epam.lab.repository;

import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.lab.model.AbstractEntity_.ID;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(final Tag entity) {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
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

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        query.select(from).orderBy(criteriaBuilder.asc(from.get(ID)));
        return entityManager.createQuery(query).getResultList();
    }
}
