package com.epam.lab.repository;

import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.lab.model.AbstractEntity_.ID;

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
        Author author = entityManager.find(Author.class, id);
        if (author != null) {
            return author;
        }  else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Author update(final Author entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public List<Author> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        Root<Author> from = query.from(Author.class);
        query.select(from).orderBy(criteriaBuilder.asc(from.get(ID)));
        return entityManager.createQuery(query).getResultList();
    }
}
