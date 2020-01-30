package com.epam.lab.dto.mapper;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("newsMapper")
public class NewsMapper extends AbstractMapper<News, NewsDto> {

    @Autowired
    public NewsMapper() {
        super(News.class, NewsDto.class);
    }
}
