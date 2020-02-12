package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class NewsController {

    NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    @ResponseBody
    public NewsDto findNews() {
        NewsDto newsDto = new NewsDto();
        newsDto.setTitle("Big news!");
        newsDto.setShortText("BLABLA!");
        newsDto.setFullText("Full text!");
        return newsDto;
    }


    @PutMapping
    @ResponseBody
    public NewsDto createNews(@RequestBody NewsDto newsDto) {
        newsService.create(newsDto);
        return newsDto;
    }


}
