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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
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
    private NewsMapper newsMapper;

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
        newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsMapper, tagMapper);
    }

    @Test
    public void shouldCreateNewsWithValidAuthor() {
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);
        when(newsRepository.findNewsByTitle(news.getTitle())).thenReturn(Boolean.FALSE);
        when(newsRepository.create(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.create(expected);
        assertEquals(expected, actual);

        verify(authorRepository, times(1)).findByAuthor(any(Author.class));
        verify(newsRepository, times(1)).findNewsByTitle(any(String.class));
        verify(newsRepository, times(1)).create(any(News.class));
        verify(newsRepository, times(1)).addNewsAuthor(anyLong(), anyLong());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenNewsIsDuplicate() {
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);
        when(newsRepository.findNewsByTitle(news.getTitle())).thenReturn(Boolean.TRUE);

        newsService.create(expected);

        verify(authorRepository, times(1)).findByAuthor(any(Author.class));
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenAuthorIsNotValid() {
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(null);

        newsService.create(expected);

        verify(authorRepository, times(1)).findByAuthor(any(Author.class));
    }

    @Test
    public void shouldCreateNewsWithAuthorValue() {
        AuthorDto authorDtoWithoutId = new AuthorDto();
        authorDtoWithoutId.setName("name");
        authorDtoWithoutId.setSurname("surname");

        NewsDto entityDto = new NewsDto();
        entityDto.setTitle("TestTitle");
        entityDto.setShortText("TestShortText");
        entityDto.setFullText("TestFullText");
        entityDto.setCreationDate(expected.getCreationDate());
        entityDto.setModificationDate(expected.getModificationDate());
        entityDto.setAuthor(authorDtoWithoutId);
        entityDto.setTags(new ArrayList<>());

        when(authorRepository.create(any(Author.class))).thenReturn(author);
        when(newsRepository.create(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.create(entityDto);
        assertEquals(expected, actual);

        verify(authorRepository, times(1)).create(any(Author.class));
        verify(newsRepository, times(1)).create(any(News.class));
        verify(newsRepository, times(1)).addNewsAuthor(anyLong(), anyLong());
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
        NewsDto entityDto = new NewsDto();
        entityDto.setTitle("TestTitle");
        entityDto.setShortText("TestShortText");
        entityDto.setFullText("TestFullText");
        entityDto.setCreationDate(expected.getCreationDate());
        entityDto.setModificationDate(expected.getModificationDate());
        entityDto.setTags(new ArrayList<>());

        when(newsRepository.update(any(News.class))).thenReturn(news);
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);

        NewsDto actual = newsService.update(entityDto);
        assertEquals(actual, expected);

        verify(newsRepository, times(1)).update(any(News.class));
        verify(authorRepository, times(1)).findByAuthor(any(Author.class));
        verify(newsRepository, times(1)).addNewsAuthor(anyLong(), anyLong());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionAfterUpdateNewsWithInvalidAuthor() {
        when(newsRepository.update(any(News.class))).thenReturn(news);
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(null);

        newsService.update(new NewsDto());

        verify(newsRepository, times(1)).update(any(News.class));
        verify(authorRepository, times(1)).findByAuthor(any(Author.class));
    }

    @Test
    public void shouldUpdateNewsAndCreateNewAuthor() {
        NewsDto newsDto = new NewsDto();
        newsDto.setTitle("TestTitle");
        newsDto.setShortText("TestShortText");
        newsDto.setFullText("TestFullText");
        newsDto.setCreationDate(expected.getCreationDate());
        newsDto.setModificationDate(expected.getModificationDate());

        AuthorDto authorDtoWithoutId = new AuthorDto();
        authorDtoWithoutId.setName("name");
        authorDtoWithoutId.setSurname("surname");
        newsDto.setAuthor(authorDtoWithoutId);

        newsDto.setTags(new ArrayList<>());

        News news = newsMapper.toEntity(newsDto);

        when(newsRepository.update(any(News.class))).thenReturn(news);
        when(authorRepository.create(any(Author.class))).thenReturn(author);

        NewsDto actual = newsService.update(newsDto);
        assertEquals(expected, actual);

        verify(newsRepository, times(1)).update(any(News.class));
        verify(authorRepository, times(1)).create(any(Author.class));

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

        List<TagDto> tagDtoList = newsService.addTagsForNews(newsId, new ArrayList<>());
        assertEquals(1, tagDtoList.size());

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

        List<TagDto> tagDtoList = newsService.addTagsForNews(newsId, new ArrayList<>());
        assertEquals(0, tagDtoList.size());

        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
        verify(tagRepository, times(1)).removeTagsByNewsId(newsId);
        verify(tagRepository, times(tags.size())).findByTag(any(Tag.class));
        verify(newsRepository, times(0)).addNewsTag(newsId, tagId);
    }

    @Test
    public void shouldAddTagsWithoutIdForNews() {
        long newsId = 11L;
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag("TestTag2");
        tags.add(tag1);

        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(tags);
        long newTagId = 1L;
        when(tagRepository.create(tag1)).thenReturn(new Tag(newTagId, "TestTag2"));

        List<TagDto> tagDtoList = newsService.addTagsForNews(newsId, new ArrayList<>());
        assertEquals(1, tagDtoList.size());

        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
        verify(tagRepository, times(1)).removeTagsByNewsId(newsId);
        verify(tagRepository, times(1)).create(tag1);
        verify(newsRepository, times(1)).addNewsTag(newsId, newTagId);
    }

    @Test
    public void shouldAddExistingTagsWithoutIdForNews() {
        long newsId = 11L;
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag("TestTag2");
        tags.add(tag1);

        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(tags);
        when(tagRepository.create(tag1)).thenReturn(null);
        Tag existingTag = new Tag(1L, "TestTag2");
        when(tagRepository.findByTagName(tag1.getName())).thenReturn(existingTag);

        List<TagDto> tagDtoList = newsService.addTagsForNews(newsId, new ArrayList<>());
        assertEquals(1, tagDtoList.size());

        verify(tagRepository, times(1)).findTagsByNewsId(newsId);
        verify(tagRepository, times(1)).removeTagsByNewsId(newsId);
        verify(tagRepository, times(1)).create(tag1);
        verify(tagRepository, times(1)).findByTagName(existingTag.getName());
        verify(newsRepository, times(1)).addNewsTag(newsId, existingTag.getId());
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
