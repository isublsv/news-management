package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.mapper.AuthorMapper;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import com.epam.lab.repository.impl.NewsRepositoryImpl;
import com.epam.lab.service.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorServiceImplTest {

    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private NewsRepository newsRepository;

    private Author author;
    private AuthorDto expected;

    @Before
    public void setUp() throws Exception {
        authorRepository = mock(AuthorRepositoryImpl.class);
        newsRepository = mock(NewsRepositoryImpl.class);
        AuthorMapper authorMapper = new AuthorMapper();
        authorService = new AuthorServiceImpl(authorRepository, newsRepository, authorMapper);

        List<News> news = new ArrayList<>();
        author = new Author(1L, "name", "surname", news);

        List<NewsDto> newsDtoList = new ArrayList<>();
        expected = new AuthorDto(1L, "name", "surname", newsDtoList);
    }

    @Test
    public void shouldCreateAuthorByDtoValue() {
        when(authorRepository.create(any(Author.class))).thenReturn(author);

        AuthorDto actual = authorService.create(new AuthorDto());
        assertEquals(actual, expected);

        verify(authorRepository, times(1)).create(any(Author.class));
    }

    @Test
    public void shouldFindAuthorById() {
        when(authorRepository.find(anyLong())).thenReturn(author);

        AuthorDto actual = authorService.find(anyLong());
        assertEquals(expected, actual);

        verify(authorRepository, times(1)).find(anyLong());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindAuthorById() {
        when(authorRepository.find(anyLong())).thenThrow(EmptyResultDataAccessException.class);

        authorRepository.find(anyLong());
    }

    @Test
    public void shouldUpdateAuthorByDtoValue() {
        when(authorRepository.update(any(Author.class))).thenReturn(author);

        AuthorDto actual = authorService.update(new AuthorDto());
        assertEquals(expected, actual);

        verify(authorRepository, times(1)).update(any(Author.class));
    }

    @Test
    public void shouldDeleteAuthorById() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);

        when(newsRepository.findNewsByAuthorId(any(Long.class))).thenReturn(idList);
        authorService.delete(anyLong());

        verify(newsRepository, times(1)).findNewsByAuthorId(anyLong());
        verify(authorRepository, times(1)).delete(anyLong());
        verify(newsRepository, times(idList.size())).delete(anyLong());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionAfterDeleteAuthorById() {
        doThrow(RepositoryException.class).when(authorRepository).delete(anyLong());
        authorService.delete(anyLong());
    }
}
