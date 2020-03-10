package com.epam.lab.repository;

import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
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
        try {
            entityManager.unwrap(Session.class).replicate(entity, ReplicationMode.IGNORE);
        } catch (ConstraintViolationException e) {
            throw new EntityDuplicatedException();
        }
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

        Author author = entity.getAuthor();
        entityManager.unwrap(Session.class).replicate(author, ReplicationMode.IGNORE);
        news.setAuthor(author);

        List<Tag> tags = news.getTags();
        tags.clear();
        try {
            entity.getTags().forEach(tag -> entityManager.unwrap(Session.class).replicate(tag, ReplicationMode.IGNORE));
        } catch (ConstraintViolationException e) {
            throw new EntityDuplicatedException();
        }
        tags.addAll(entity.getTags());

        return entityManager.merge(news);
    }

    @Override
    public void delete(final Long id) {
        News news = find(id);
        //remove associations
        news.setAuthor(null);
        news.setTags(null);
        //synchronize and clear context
        entityManager.flush();
        entityManager.clear();
        //remove entity
        entityManager.remove(find(id));
    }

    @Override
    public List<News> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = criteriaBuilder.createQuery(News.class);
        Root<News> from = query.from(News.class);
        query.select(from);
        return entityManager.createQuery(query).getResultList();
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
    public List<Tag> addTagsForNews(final Long newsId, final List<Tag> tags) {
        News news = find(newsId);
        List<Tag> newsTags = news.getTags();
        for (Tag tag : tags) {
            try {
                entityManager.unwrap(Session.class).replicate(tag, ReplicationMode.IGNORE);
                if (!newsTags.contains(tag)) {
                    news.addTag(tag);
                }
            } catch (ConstraintViolationException e) {
                throw new EntityDuplicatedException();
            }
        }
        return newsTags;
    }

    @Override
    public List<News> searchBy(final SearchCriteria searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        SearchNewsQuery searchNewsQuery = new SearchNewsQuery(builder, searchCriteria);
        return entityManager.createQuery(searchNewsQuery.buildQuery()).getResultList();
    }
}
