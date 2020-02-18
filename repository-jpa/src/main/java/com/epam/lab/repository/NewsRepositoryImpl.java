package com.epam.lab.repository;

import com.epam.lab.model.News;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @Override
    public Long countAllNews() {
        return null;
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
        return null;
    }

    @Override
    public List<News> searchBy(final String sqlQuery) {
        return null;
    }

    @Override
    public News create(final News entity) {
        return null;
    }

    @Override
    public News find(final Long id) {
        return null;
    }

    @Override
    public News update(final News entity) {
        return null;
    }

    @Override
    public void delete(final Long id) {

    }
}
