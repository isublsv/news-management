package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
    
    private final NewsService newsService;

    @Autowired
    public NewsController(final NewsService newsService) {
        this.newsService = newsService;
    }
    
    @PostMapping(value = "/add")
    public void addNews(@RequestBody NewsDto newsDto) {
        newsService.create(newsDto);
    }

    @GetMapping(value = "/find")
    public ResponseEntity<NewsDto> findNews(@RequestParam long id) {
        return ResponseEntity.ok(newsService.find(id));
    }
}
