package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Page;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
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
    public NewsDto createNews(@RequestBody @Valid final NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @GetMapping(value = "/find/{id}", produces = APPLICATION_JSON_VALUE)
    public NewsDto findNewsById(@PathVariable @Positive(message = "Id must positive") final Long id) {
        return newsService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public NewsDto editAuthor(@RequestBody @Valid final NewsDto newsDto) {
        return newsService.update(newsDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable  @Positive(message = "Id must positive") final Long id) {
        newsService.delete(id);
    }
    
    @GetMapping("/findAll")
    public List<NewsDto> findAllNews() {
        return newsService.findAll();
    }

    @PutMapping(value = "/add_tags/{newsId}", produces = APPLICATION_JSON_VALUE)
    public List<TagDto> addTagForNews(@PathVariable
                                      @Positive(message = "News Id must positive") final Long newsId,
                                      @RequestBody @NotNull final List<@Valid TagDto> tagDtos) {
        return newsService.addTagsForNews(newsId, tagDtos);
    }

    @GetMapping(value = "/search_by", produces = APPLICATION_JSON_VALUE)
    public Page<NewsDto> searchNewsBy(@ModelAttribute @Valid final SearchCriteriaDto sc, BindingResult bindingResult) {
        return newsService.searchBy(sc);
    }

    @GetMapping("/countAll")
    public Long countAllNews() {
        return newsService.countAllNews();
    }
}
