package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.AuthorMapper;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.AuthorRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceImplTest {

    private AuthorService authorService;
    private AuthorRepository authorRepository;

    private Author author;
    private AuthorDto expected;

    @Before
    public void setUp() {
        authorRepository = mock(AuthorRepositoryImpl.class);
        AuthorMapper authorMapper = new AuthorMapper();
        authorService = new AuthorServiceImpl(authorRepository, authorMapper);

        author = new Author(1L, "name", "surname");

        expected = new AuthorDto(1L, "name", "surname");
    }

    @Test
    public void shouldCreateAuthorByDtoValue() {
        when(authorRepository.create(any(Author.class))).thenReturn(author);

        AuthorDto actual = authorService.create(new AuthorDto());
        assertEquals(expected, actual);

        verify(authorRepository).create(any(Author.class));
    }

    @Test
    public void shouldFindAuthorById() {
        when(authorRepository.find(anyLong())).thenReturn(author);

        AuthorDto actual = authorService.find(anyLong());
        assertEquals(expected, actual);

        verify(authorRepository).find(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionAfterFindAuthorById() {
        when(authorRepository.find(anyLong())).thenThrow(EntityNotFoundException.class);

        authorRepository.find(anyLong());
    }

    @Test
    public void shouldUpdateAuthorByDtoValue() {
        when(authorRepository.update(any(Author.class))).thenReturn(author);

        AuthorDto actual = authorService.update(new AuthorDto());
        assertEquals(expected, actual);

        verify(authorRepository).update(any(Author.class));
    }

    @Test
    public void shouldDeleteAuthorById() {
        authorService.delete(anyLong());

        verify(authorRepository).delete(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeleteAuthorById() {
        doThrow(EntityNotFoundException.class).when(authorRepository).delete(anyLong());

        authorService.delete(anyLong());
    }
}