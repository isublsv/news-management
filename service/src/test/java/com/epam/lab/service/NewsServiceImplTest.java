package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.AuthorRepositoryImpl;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.NewsRepositoryImpl;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.TagRepositoryImpl;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
    public static void setUp() {
        news = new News();
        news.setId(1L);
        news.setTitle("TestTitle");
        news.setShortText("TestShortText");
        news.setFullText("TestFullText");
        LocalDate newsDate = LocalDate.now();
        news.setCreationDate(newsDate);
        news.setModificationDate(newsDate);

        author = new Author(1L, "name", "surname");
        news.setAuthor(author);
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
        authorRepository = mock(AuthorRepositoryImpl.class);
        tagRepository = mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsMapper, tagMapper);
    }

    @Test
    public void shouldCreateNewsWithExistingAuthor() {
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);
        when(newsRepository.findNewsByTitle(any(String.class))).thenReturn(Boolean.FALSE);
        when(newsRepository.create(any(News.class))).thenReturn(news);

        NewsDto initNewsDto = createInitNewsDto();
        initNewsDto.getAuthor().setId(1L);

        NewsDto actual = newsService.create(initNewsDto);
        assertEquals(expected, actual);

        verify(authorRepository).findByAuthor(any(Author.class));
        verify(newsRepository).findNewsByTitle(any(String.class));
        verify(newsRepository).create(any(News.class));
        verify(newsRepository).addNewsAuthor(anyLong(), anyLong());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenNewsIsDuplicate() {
        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);
        when(newsRepository.findNewsByTitle(news.getTitle())).thenReturn(Boolean.TRUE);

        newsService.create(expected);

        verify(authorRepository).findByAuthor(any(Author.class));
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionWhenAuthorIsNotValid() {
        when(authorRepository.findByAuthor(any(Author.class))).thenThrow(EmptyResultDataAccessException.class);

        newsService.create(expected);

        verify(authorRepository).findByAuthor(any(Author.class));
    }

    @Test
    public void shouldCreateNewsWithAuthorValue() {
        when(authorRepository.create(any(Author.class))).thenReturn(author);
        when((newsRepository.findNewsByTitle(any(String.class)))).thenReturn(Boolean.FALSE);
        when(newsRepository.create(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.create(createInitNewsDto());
        assertEquals(expected, actual);

        verify(authorRepository).create(any(Author.class));
        verify(newsRepository).create(any(News.class));
        verify(newsRepository).addNewsAuthor(anyLong(), anyLong());
    }

    @Test
    public void shouldFindNewsById() {
        long newsId = 1L;
        when(newsRepository.find(newsId)).thenReturn(news);
        when(authorRepository.find(newsId)).thenReturn(author);
        when(tagRepository.findTagsByNewsId(newsId)).thenReturn(new ArrayList<>());

        NewsDto actual = newsService.find(newsId);

        assertEquals(expected, actual);

        verify(newsRepository).find(anyLong());
        verify(authorRepository).find(anyLong());
        verify(tagRepository).findTagsByNewsId(anyLong());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindNewsById() {
        when(newsRepository.find(anyLong())).thenThrow(EmptyResultDataAccessException.class);
        newsService.find(anyLong());
    }

    @Test
    public void shouldUpdateNews() {
        NewsDto initNewsDto = createInitNewsDto();
        initNewsDto.setId(1L);
        initNewsDto.getAuthor().setId(1L);

        when(authorRepository.findByAuthor(any(Author.class))).thenReturn(author);
        when(newsRepository.findNewsByTitle(any(String.class))).thenReturn(Boolean.FALSE);
        when(newsRepository.update(any(News.class))).thenReturn(news);

        NewsDto actual = newsService.update(initNewsDto);
        assertEquals(expected, actual);

        verify(authorRepository).findByAuthor(any(Author.class));
        verify(newsRepository).findNewsByTitle(any(String.class));
        verify(newsRepository).update(any(News.class));
        verify(newsRepository).addNewsAuthor(anyLong(), anyLong());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionIfTryToUpdateNewsWithInvalidAuthor() {
        NewsDto newsDto = createInitNewsDto();
        newsDto.setId(1L);
        newsDto.getAuthor().setId(1L);

        when(authorRepository.findByAuthor(any(Author.class))).thenThrow(EmptyResultDataAccessException.class);

        newsService.update(newsDto);

        verify(authorRepository).findByAuthor(any(Author.class));
    }

    @Test
    public void shouldUpdateNewsAndCreateNewAuthor() {
        NewsDto newsDto = createInitNewsDto();
        newsDto.setId(1L);

        when(newsRepository.update(any(News.class))).thenReturn(news);
        when(authorRepository.create(any(Author.class))).thenReturn(author);

        assertEquals(expected, newsService.update(newsDto));

        verify(newsRepository).update(any(News.class));
        verify(authorRepository).create(any(Author.class));
    }

    @Test
    public void shouldDeleteNewsById() {
        newsService.delete(anyLong());

        verify(newsRepository).delete(anyLong());
    }

    @Test
    public void shouldAddValidTagsForNews() {
        List<Tag> tags = createTagsWithId();

        when(tagRepository.findTagsByNewsId(anyLong())).thenReturn(tags);
        when(tagRepository.findByTag(any(Tag.class))).thenReturn(tags.get(0));

        List<TagDto> tagDtos = newsService.addTagsForNews(anyLong(), new ArrayList<>());
        assertEquals(1, tagDtos.size());

        verify(tagRepository).findTagsByNewsId(anyLong());
        verify(tagRepository).removeTagsByNewsId(anyLong());
        verify(tagRepository, times(tags.size())).findByTag(any(Tag.class));
        verify(newsRepository, times(tags.size())).addNewsTag(anyLong(), anyLong());
    }

    @Test
    public void shouldDoNotAddInvalidTagsForNews() {
        List<Tag> tags = createTagsWithId();

        when(tagRepository.findTagsByNewsId(anyLong())).thenReturn(tags);
        when(tagRepository.findByTag(any(Tag.class))).thenThrow(EmptyResultDataAccessException.class);

        List<TagDto> tagDtos = newsService.addTagsForNews(anyLong(), new ArrayList<>());
        assertEquals(0, tagDtos.size());

        verify(tagRepository).findTagsByNewsId(anyLong());
        verify(tagRepository).removeTagsByNewsId(anyLong());
        verify(tagRepository, times(tags.size())).findByTag(any(Tag.class));
        verify(newsRepository, never()).addNewsTag(anyLong(), anyLong());
    }

    private List<Tag> createTagsWithId() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "TestTag2"));
        return tags;
    }

    @Test
    public void shouldAddExistingTagsWithoutIdForNews() {
        List<Tag> tags = createTagsWithoutId();

        when(tagRepository.findTagsByNewsId(anyLong())).thenReturn(tags);
        when(tagRepository.findByTagName(any(String.class))).thenReturn(new Tag(1L, "TestTag2"));

        List<TagDto> tagDtos = newsService.addTagsForNews(anyLong(), new ArrayList<>());
        assertEquals(1, tagDtos.size());

        verify(tagRepository).findTagsByNewsId(anyLong());
        verify(tagRepository).removeTagsByNewsId(anyLong());
        verify(tagRepository).findByTagName(any(String.class));
        verify(newsRepository).addNewsTag(anyLong(), anyLong());
    }

    @Test
    public void shouldAddTagsWithoutIdForNews() {
        List<Tag> tags = createTagsWithoutId();

        when(tagRepository.findTagsByNewsId(anyLong())).thenReturn(tags);
        when(tagRepository.findByTagName(any(String.class))).thenThrow(EmptyResultDataAccessException.class);
        when(tagRepository.create(any(Tag.class))).thenReturn(new Tag(1L, "TestTag2"));

        List<TagDto> tagDtos = newsService.addTagsForNews(anyLong(), new ArrayList<>());
        assertEquals(1, tagDtos.size());

        verify(tagRepository).findTagsByNewsId(anyLong());
        verify(tagRepository).removeTagsByNewsId(anyLong());
        verify(tagRepository).findByTagName(any(String.class));
        verify(tagRepository).create(any(Tag.class));
        verify(newsRepository).addNewsTag(anyLong(), anyLong());
    }

    private List<Tag> createTagsWithoutId() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("TestTag2"));
        return tags;
    }

    @Test
    public void shouldFindCountAllNews() {
        Long expected = 1L;
        when(newsRepository.countAllNews()).thenReturn(expected);

        assertEquals(expected, newsService.countAllNews());

        verify(newsRepository).countAllNews();
    }

    private NewsDto createInitNewsDto() {
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
        return newsDto;
    }
}
