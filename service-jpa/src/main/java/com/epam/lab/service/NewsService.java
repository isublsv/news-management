package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Page;

import java.util.List;

public interface NewsService extends Service<NewsDto> {

    List<TagDto> addTagsForNews(Long newsId, List<TagDto> tagDtos);
    
    Long countAllNews();

    Page<NewsDto> searchBy(SearchCriteriaDto searchCriteriaDtoValue);
}
