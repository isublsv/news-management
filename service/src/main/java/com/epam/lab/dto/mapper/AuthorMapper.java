package com.epam.lab.dto.mapper;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("authorMapper")
public class AuthorMapper extends AbstractMapper<Author, AuthorDto> {

    @Autowired
    public AuthorMapper() {
        super(Author.class, AuthorDto.class);
    }
}
