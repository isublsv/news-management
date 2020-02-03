package com.epam.lab.controller;

import com.epam.lab.dto.TagDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(final TagService tagServiceValue) {
        tagService = tagServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody final TagDto tagDto) {
        TagDto tagDtoWithId = new TagDto();
        try {
            tagDtoWithId = tagService.create(tagDto);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return tagDtoWithId;
    }

    @GetMapping("/find/{id}")
    public TagDto findTagById(@PathVariable final long id) {
        return tagService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public TagDto editTag(@RequestBody final TagDto tagDto) {
        return tagService.update(tagDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTag(@PathVariable final long id) {
        tagService.delete(id);
    }
}
