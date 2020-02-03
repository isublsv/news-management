package com.epam.lab.repository;

import com.epam.lab.model.News;

import java.util.List;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();

    void addNewsAuthor(long newsId, long authorId);

    void addNewsTag(long newsId, long tagId);

    List<News> findNewsByAuthorId(long authorId);
}
