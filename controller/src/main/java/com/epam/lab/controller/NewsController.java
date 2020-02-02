package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class NewsController {

    private NewsService newsService;

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

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName("Name");
        authorDto.setSurname("Surname");
        newsDto.setAuthorDto(authorDto);

        TagDto tagDto = new TagDto("Tag1");
        TagDto tagDto1 = new TagDto("Tag2");
        List<TagDto> tagDtos = new ArrayList<>();
        tagDtos.add(tagDto);
        tagDtos.add(tagDto1);

        newsDto.setTagDtos(tagDtos);
        return newsDto;
    }

    @PutMapping
    @ResponseBody
    public NewsDto createNews(@RequestBody NewsDto newsDto) throws Exception {
        return newsService.create(newsDto);
    }


}
