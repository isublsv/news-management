package com.epam.lab.repository;

import com.epam.lab.model.News;

import java.util.List;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();

    void addNewsAuthor(Long newsId, Long authorId);

    void addNewsTag(Long newsId, Long tagId);

    List<News> findNewsByAuthorId(Long authorId);
}
