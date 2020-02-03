package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(final AuthorService authorServiceValue) {
        authorService = authorServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody final AuthorDto authorDto) {
        AuthorDto authorDtoWithId = new AuthorDto();
        try {
            authorDtoWithId = authorService.create(authorDto);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return authorDtoWithId;
    }

    @GetMapping("/find/{id}")
    public AuthorDto findAuthorById(@PathVariable final long id) {
        return authorService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthorDto editAuthor(@RequestBody final AuthorDto authorDto) {
        return authorService.update(authorDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable final long id) {
        authorService.delete(id);
    }
}
