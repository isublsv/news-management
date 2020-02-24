package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(final NewsService newsServiceValue) {
        this.newsService = newsServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Valid
    public NewsDto createNews(@RequestBody @Valid final NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @GetMapping(value = "/find/{id}", produces = APPLICATION_JSON_VALUE)
    @Valid
    public NewsDto findNewsById(@PathVariable @NotNull
                                @Positive(message = "Id cannot be null and must positive") final Long id) {
        return newsService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Valid
    public NewsDto editAuthor(@RequestBody @Valid final NewsDto newsDto) {
        return newsService.update(newsDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable @NotNull
                             @Positive(message = "Id cannot be null and must positive") final Long id) {
        newsService.delete(id);
    }

    @PutMapping(value = "/add_tags", produces = APPLICATION_JSON_VALUE)
    public List<@Valid TagDto> addTagForNews(@RequestParam @NotNull
                                             @Positive(message = "Id cannot be null and must positive") final Long newsId,
                                             @RequestParam @NotNull final List<@Valid TagDto> tagDtos) {
        return newsService.addTagsForNews(newsId, tagDtos);
    }

    @GetMapping(value = "/search_by", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<@Valid NewsDto> searchNewsBy(@ModelAttribute @Valid final SearchCriteriaDto sc) {
        return newsService.searchBy(sc);
    }

    @GetMapping("/findAll")
    public Long findAllNews() {
        return newsService.countAllNews();
    }
}
