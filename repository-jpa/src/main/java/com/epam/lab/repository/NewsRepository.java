package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.Page;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;

import java.util.List;

public interface NewsRepository extends Repository<News> {

    Long countAllNews();

    Page<News> searchBy(SearchCriteria searchCriteria);

    List<Tag> addTagsForNews(Long newsId, List<Tag> tags);
}
