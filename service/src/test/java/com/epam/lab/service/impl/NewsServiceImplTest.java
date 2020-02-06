package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NewsServiceImplTest {

    private NewsService newsService;

    private NewsRepository newsRepository;
    private AuthorRepository authorRepository;
    private TagRepository tagRepository;

    private static News news;
    private static NewsDto expected;
    private static Author author;

    @BeforeClass
    public static void setUp() throws Exception {
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

    @Before
    public void beforeMethod() {
        newsRepository = mock(NewsRepositoryImpl.class);
        authorRepository = mock(AuthorRepositoryImpl.class);
        tagRepository = mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsMapper, tagMapper);
    }

    @Test
    public void shouldCreateNews() {
    }

    @Test
    public void shouldFindNewsById() {
        long newsId = 1L;
        when(newsRepository.find(newsId)).thenReturn(news);
        when(authorRepository.find(newsId)).thenReturn(author);
        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(new ArrayList<>());

        NewsDto actual = newsService.find(newsId);

        assertEquals(expected, actual);

        verify(newsRepository, times(1)).find(newsId);
        verify(authorRepository, times(1)).find(newsId);
        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindNewsById() {
        long id = 5L;
        when(newsRepository.find(id)).thenThrow(EmptyResultDataAccessException.class);
        newsService.find(id);
    }

    @Test
    public void shouldUpdateNews() {

    }

    @Test
    public void shouldDeleteNewsById() {
        long newsId = 6L;
        newsService.delete(newsId);
        verify(newsRepository, times(1)).delete(newsId);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionAfterDeleteNewsById() {
        long tagId = 7L;
        doThrow(RepositoryException.class).when(newsRepository).delete(tagId);
        newsService.delete(tagId);
    }

    @Test
    public void shouldAddValidTagsForNews() {
        long newsId = 10L;
        List<Tag> tags = new ArrayList<>();
        long tagId = 12L;
        Tag tag1 = new Tag(tagId, "TestTag1");
        tags.add(tag1);

        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(tags);
        when(tagRepository.findByTag(any(Tag.class))).thenReturn(tag1);

        newsService.addTagsForNews(newsId, new ArrayList<>());

        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
        verify(tagRepository, times(1)).removeTagsByNewsId(newsId);
        verify(tagRepository, times(tags.size())).findByTag(any(Tag.class));
        verify(newsRepository, times(tags.size())).addNewsTag(newsId, tagId);
    }

    @Test
    public void shouldDoNotAddInvalidTagsForNews() {
        long newsId = 11L;
        List<Tag> tags = new ArrayList<>();
        long tagId = 13L;
        Tag tag1 = new Tag(tagId, "TestTag2");
        tags.add(tag1);

        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(tags);
        when(tagRepository.findByTag(any(Tag.class))).thenReturn(null);

        newsService.addTagsForNews(newsId, new ArrayList<>());

        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
        verify(tagRepository, times(1)).removeTagsByNewsId(newsId);
        verify(tagRepository, times(tags.size())).findByTag(any(Tag.class));
        verify(newsRepository, times(tags.size())).addNewsTag(newsId, tagId);
    }

    @Test
    public void shouldFindCountAllNews() {
        Long expected = 14L;
        when(newsRepository.countAllNews()).thenReturn(expected);

        Long actual = newsService.countAllNews();
        assertEquals(expected, actual);

        verify(newsRepository, times(1)).countAllNews();
    }

    @Test
    public void shouldSearchBy() {
    }
}
