package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;

import java.util.List;

public interface NewsService extends Service<NewsDto> {

    void addTagsForNews(NewsDto newsDto, List<TagDto> tagDtos);
    
    Long countAllNews();
}
