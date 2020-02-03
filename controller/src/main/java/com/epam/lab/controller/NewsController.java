package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(final NewsService newsServiceValue) {
        this.newsService = newsServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public NewsDto createNews(@RequestBody final NewsDto newsDto) throws ServiceException {
        return newsService.create(newsDto);
    }

    @GetMapping("/find/{id}")
    public NewsDto findNewsById(@PathVariable final long id) {
        return newsService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDto editAuthor(@RequestBody final NewsDto newsDto) {
        return newsService.update(newsDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable final long id) {
        newsService.delete(id);
    }


}
