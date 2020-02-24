package com.epam.lab.repository;

import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        entityManager.unwrap(Session.class).replicate(entity, ReplicationMode.IGNORE);
        return entity;
    }

    @Override
    public News find(final Long id) {
        News news = entityManager.find(News.class, id);
        if (news != null) {
            return news;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public News update(final News entity) {
        News news = find(entity.getId());

        news.setTitle(entity.getTitle());
        news.setShortText(entity.getShortText());
        news.setFullText(entity.getFullText());
        news.setModificationDate(LocalDate.now());

        long authorId = entity.getAuthor().getId();
        if (authorId != 0) {
            Author author = entityManager.find(Author.class, authorId);
            news.setAuthor(author);
        } else {
            news.setAuthor(entity.getAuthor());
        }
        List<Tag> tags = news.getTags();
        tags.clear();
        tags.addAll(entity.getTags());

        return entityManager.merge(news);
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
    public List<News> searchBy(final SearchCriteria searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        SearchNewsQuery searchNewsQuery = new SearchNewsQuery(builder, searchCriteria);
        return entityManager.createQuery(searchNewsQuery.buildQuery()).getResultList();
    }
}
