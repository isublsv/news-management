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
        News news = find(entity.getId());
        news.setTitle(entity.getTitle());
        news.setShortText(entity.getShortText());
        news.setFullText(entity.getFullText());
        news.setCreationDate(entity.getCreationDate());
        news.setModificationDate(LocalDate.now());
        entityManager.merge(news);
        return news;
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

    @Override
    public void addNewsAuthor(final Long newsId, final Long authorId) {

    }

    @Override
    public void removeNewsAuthor(final Long newsId) {

    }

    @Override
    public void addNewsTag(final Long newsId, final Long tagId) {

    }

    @Override
    public Boolean findNewsByTitle(final String title) {
        return null;
    }

    @Override
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
        return null;
    }


}
