package com.epam.lab.dto;

import com.epam.lab.model.News;
import org.springframework.stereotype.Component;

@Component("newsMapper")
public class NewsMapper extends AbstractMapper<News, NewsDto> {

    public NewsMapper() {
        super(News.class, NewsDto.class);
    }
}
