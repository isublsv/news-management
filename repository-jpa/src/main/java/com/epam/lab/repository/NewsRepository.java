package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;

import java.util.List;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();

    List<News> searchBy(SearchCriteria searchCriteria);
}
