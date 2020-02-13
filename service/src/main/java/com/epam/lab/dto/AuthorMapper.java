package com.epam.lab.dto;

import com.epam.lab.model.Author;
import org.springframework.stereotype.Component;

@Component("authorMapper")
public class AuthorMapper extends AbstractMapper<Author, AuthorDto> {

    public AuthorMapper() {
        super(Author.class, AuthorDto.class);
    }
}
