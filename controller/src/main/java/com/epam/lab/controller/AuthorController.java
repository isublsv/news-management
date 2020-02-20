package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/author")
@Validated
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(final AuthorService authorServiceValue) {
        authorService = authorServiceValue;
    }

    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Valid
    public AuthorDto createAuthor(@RequestBody @Valid final AuthorDto authorDto) {
        return authorService.create(authorDto);
    }

    @GetMapping(value = "/find/{id}", produces = APPLICATION_JSON_VALUE)
    @Valid
    public AuthorDto findAuthorById(@PathVariable @NotNull
    @Positive(message = "Id cannot be null and must positive") final Long id) {
        return authorService.find(id);
    }

    @PutMapping(value = "/edit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Valid
    public AuthorDto editAuthor(@RequestBody @Valid final AuthorDto authorDto) {
        return authorService.update(authorDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable @NotNull
    @Positive(message = "Id cannot be null and must positive") final Long id) {
        authorService.delete(id);
    }
}
