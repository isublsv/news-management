package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;

public interface NewsService extends Service<NewsDto> {

    Long countAllNews();
}
