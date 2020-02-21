package com.epam.lab.repository;

import com.epam.lab.model.Author;
import com.epam.lab.model.Author_;
import com.epam.lab.model.News;
import com.epam.lab.model.News_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public News create(final News entity) {
        LocalDate date = LocalDate.now();
        entity.setCreationDate(date);
        entity.setModificationDate(date);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public News find(final Long id) {
        return entityManager.find(News.class, id);
    }

    @Override
    public News update(final News entity) {
        entity.setModificationDate(LocalDate.now());

        //update author and tags
/*        news.getTags().clear();
        news.getTags().addAll(entity.getTags());*/

        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public Long countAllNews() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> root = criteriaQuery.from(News.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Long> findNewsByAuthorId(final Long authorId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<News> root = query.from(News.class);
        Join<News, Author> author = root.join(News_.AUTHOR);
        query.select(root.get(News_.ID)).where(criteriaBuilder.equal(author.get(Author_.ID), authorId));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<News> searchBy(final String sqlQuery) {
        return Collections.emptyList();
    }


}
