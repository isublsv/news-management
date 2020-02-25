package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.SearchCriteriaMapper;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.NewsRepositoryImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsServiceImplTest {

    private NewsService newsService;

    private NewsRepository newsRepository;

    private static News news;
    private static NewsDto expected;

    @BeforeClass
    public static void setUp() {
        news = new News();
        news.setId(1L);
        news.setTitle("TestTitle");
        news.setShortText("TestShortText");
        news.setFullText("TestFullText");
        LocalDate newsDate = LocalDate.now();
        news.setCreationDate(newsDate);
        news.setModificationDate(newsDate);

        news.setAuthor(new Author(1L, "name", "surname"));
        news.setTags(new ArrayList<>());

        expected = new NewsDto();
        expected.setId(1L);
        expected.setTitle("TestTitle");
        expected.setShortText("TestShortText");
        expected.setFullText("TestFullText");
        LocalDate newsDtoDate = LocalDate.now();
        expected.setCreationDate(newsDtoDate);
        expected.setModificationDate(newsDtoDate);

        AuthorDto authorDto = new AuthorDto(1L, "name", "surname");

        expected.setAuthor(authorDto);
        expected.setTags(new ArrayList<>());
    }

    @Before
    public void beforeMethod() {
        newsRepository = mock(NewsRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        SearchCriteriaMapper searchCriteriaMapper = new SearchCriteriaMapper();
        newsService = new NewsServiceImpl(newsRepository, newsMapper, tagMapper, searchCriteriaMapper);
    }

    @Test
    public void shouldCreateNewsByDtoValue() {
        when(newsRepository.create(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.create(new NewsDto());
        assertEquals(expected, actual);

        verify(newsRepository).create(any(News.class));
    }

    @Test
    public void shouldFindNewsById() {
        when(newsRepository.find(anyLong())).thenReturn(news);

        NewsDto actual = newsService.find(anyLong());
        assertEquals(expected, actual);

        verify(newsRepository).find(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenFindNewsById() {
        when(newsRepository.find(anyLong())).thenThrow(EntityNotFoundException.class);

        newsService.find(anyLong());
        verify(newsRepository).find(anyLong());
    }

    @Test
    public void shouldUpdateNewsByDtoValue() {
        when(newsRepository.update(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.update(new NewsDto());
        assertEquals(expected, actual);

        verify(newsRepository).update(any(News.class));
    }

    @Test
    public void shouldDeleteNewsById() {
        newsService.delete(anyLong());

        verify(newsRepository).delete(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeleteTagByIdIfIdNotExist() {
        doThrow(EntityNotFoundException.class).when(newsRepository).delete(anyLong());

        newsService.delete(anyLong());
    }

    @Test
    public void shouldAddTagsForNews() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "Tag"));

        List<TagDto> expected = new ArrayList<>();
        expected.add(new TagDto(1L, "Tag"));

        when(newsRepository.addTagsForNews(anyLong(), anyList())).thenReturn(tags);

        List<TagDto> actual = newsService.addTagsForNews(anyLong(), anyList());
        assertEquals(expected, actual);

        verify(newsRepository).addTagsForNews(anyLong(), anyList());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenAddTagsForNews() {
        when(newsRepository.addTagsForNews(anyLong(), anyList())).thenThrow(EntityNotFoundException.class);

        newsService.addTagsForNews(anyLong(), anyList());

        verify(newsRepository).addTagsForNews(anyLong(), anyList());
    }

    @Test(expected = EntityDuplicatedException.class)
    public void shouldThrowExceptionWhenAddTagExist() {
        when(newsRepository.addTagsForNews(anyLong(), anyList())).thenThrow(EntityDuplicatedException.class);

        newsService.addTagsForNews(anyLong(), anyList());

        verify(newsRepository).addTagsForNews(anyLong(), anyList());
    }

    @Test
    public void shouldCountAllNews() {
        Long expected = 1L;
        when(newsRepository.countAllNews()).thenReturn(expected);

        assertEquals(expected, newsService.countAllNews());

        verify(newsRepository).countAllNews();
    }
}