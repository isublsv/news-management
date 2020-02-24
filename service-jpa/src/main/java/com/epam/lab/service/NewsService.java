package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.dto.TagDto;

import java.util.List;

public interface NewsService extends Service<NewsDto> {

    List<TagDto> addTagsForNews(Long newsId, List<TagDto> tagDtos);
    
    Long countAllNews();

    List<NewsDto> searchBy(SearchCriteriaDto searchCriteriaDtoValue);
}
