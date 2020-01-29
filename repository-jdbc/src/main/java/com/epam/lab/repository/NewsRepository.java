package com.epam.lab.repository;

import com.epam.lab.model.News;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();
}
