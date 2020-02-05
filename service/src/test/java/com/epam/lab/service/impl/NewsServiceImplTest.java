package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import com.epam.lab.repository.impl.NewsRepositoryImpl;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.service.NewsService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    private static NewsService newsService;

    private static NewsRepository newsRepository;
    private static AuthorRepository authorRepository;
    private static TagRepository tagRepository;

    private static News news;
    private static NewsDto expected;
    private static Author author;

    @BeforeClass
    public static void setUp() throws Exception {
        newsRepository = mock(NewsRepositoryImpl.class);
        authorRepository = mock(AuthorRepositoryImpl.class);
        tagRepository = mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsMapper, tagMapper);

        news = new News();
        news.setId(1L);
        news.setTitle("TestTitle");
        news.setShortText("TestShortText");
        news.setFullText("TestFullText");
        LocalDate newsDate = LocalDate.now();
        news.setCreationDate(newsDate);
        news.setModificationDate(newsDate);

        List<News> authorNewsList = new ArrayList<>();
        author = new Author(1L, "name", "surname", authorNewsList);

        news.setAuthor(author);

        List<Tag> newsTags = new ArrayList<>();
        news.setTags(newsTags);

        expected = new NewsDto();
        expected.setId(1L);
        expected.setTitle("TestTitle");
        expected.setShortText("TestShortText");
        expected.setFullText("TestFullText");
        LocalDate newsDtoDate = LocalDate.now();
        expected.setCreationDate(newsDtoDate);
        expected.setModificationDate(newsDtoDate);

        List<NewsDto> authorDtoNewsList = new ArrayList<>();
        AuthorDto authorDto = new AuthorDto(1L, "name", "surname", authorDtoNewsList);

        expected.setAuthor(authorDto);

        List<TagDto> newsDtoTags = new ArrayList<>();
        expected.setTags(newsDtoTags);
    }

    @Test
    public void create() {
    }

    @Test
    public void shouldFindNewsById() {
        long newsId = 1L;
        when(newsRepository.find(newsId)).thenReturn(news);
        when(authorRepository.find(anyLong())).thenReturn(author);
        when(tagRepository.findTagsByNewsId(anyLong())).thenReturn(new ArrayList<>());

        NewsDto actual = newsService.find(newsId);

        assertEquals(expected, actual);

        verify(newsRepository, times(1)).find(newsId);
        verify(authorRepository, times(1)).find(anyLong());
        verify(tagRepository, times(1)).findTagsByNewsId(anyLong());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindNewsById() {
        long id = 2L;
        when(newsRepository.find(id)).thenThrow(EmptyResultDataAccessException.class);
        newsService.find(id);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        newsService.delete(anyLong());

        verify(newsRepository, times(1)).delete(anyLong());
    }

    @Test
    public void addTagsForNews() {

    }

    @Test
    public void shouldFindCountAllNews() {
        Long expected = 1L;
        when(newsRepository.countAllNews()).thenReturn(expected);

        Long actual = newsService.countAllNews();
        assertEquals(expected, actual);

        verify(newsRepository, times(1)).countAllNews();
    }

    @Test
    public void searchBy() {
    }
}