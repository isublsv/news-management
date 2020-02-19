package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    @Override
    public Tag findByTag(final Tag tag) {
        return null;
    }

    @Override
    public Tag findByTagName(final String name) {
        return null;
    }

    @Override
    public List<Tag> findTagsByNewsId(final Long newsId) {
        News news = entityManager.find(News.class, newsId);
        return news.getTags();
    }

    @Override
    public void removeTagsByNewsId(final Long newsId) {

    }
}
