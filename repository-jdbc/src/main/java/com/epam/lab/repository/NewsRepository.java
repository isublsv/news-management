package com.epam.lab.repository;

import com.epam.lab.model.News;

import java.util.List;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();

    void addNewsAuthor(Long newsId, Long authorId);

    boolean addNewsTag(Long newsId, Long tagId);
    
    Boolean findNewsByTitle(String title);

    List<Long> findNewsByAuthorId(Long authorId);

    List<News> searchBy(String sqlQuery);
}
