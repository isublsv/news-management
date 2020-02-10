package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteria;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ParameterizedSearchTest {
    private static News news;

    private NewsService newsService;
    private NewsRepository newsRepository;

    private SearchCriteria searchCriteria;
    private List<NewsDto> expectedNewsDtoList;

    public ParameterizedSearchTest(final SearchCriteria scValue, List<NewsDto> expectedNewsDtoListValue) {
        searchCriteria = scValue;
        expectedNewsDtoList = expectedNewsDtoListValue;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Set<String> tags = new HashSet<>();
        tags.add("Tag");

        Set<String> orderBy = new LinkedHashSet<>();
        orderBy.add("date");

        List<NewsDto> dtoList = new ArrayList<>();
        dtoList.add(createNewsDto());

        return Arrays.asList(
                new Object[][]{
                        {new SearchCriteria("name", "surname", tags, orderBy, true), dtoList},
                        {new SearchCriteria("","surname", tags, orderBy,true), dtoList},
                        {new SearchCriteria(null, "surname", tags, orderBy, true), dtoList},
                        {new SearchCriteria("name", "", tags, orderBy,true), dtoList},
                        {new SearchCriteria("name", null, tags, orderBy, true), dtoList},
                        {new SearchCriteria("name", null, Collections.emptySet(), orderBy,true), dtoList},
                        {new SearchCriteria("name", null, tags, Collections.emptySet(), true), dtoList},
                        {new SearchCriteria(null, null, tags, orderBy, true), dtoList},
                });
    }

    @Before
    public void beforeMethod() {
        newsRepository = mock(NewsRepositoryImpl.class);
        AuthorRepository authorRepository = mock(AuthorRepositoryImpl.class);
        TagRepository tagRepository = mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper();
        TagMapper tagMapper = new TagMapper();
        newsService = new NewsServiceImpl(newsRepository, authorRepository, tagRepository, newsMapper, tagMapper);

        createInitNewsEntity();
    }

    @Test
    public void shouldSearchBy() {
        List<News> newsList = new ArrayList<>();
        newsList.add(news);

        when(newsRepository.searchBy(any(String.class))).thenReturn(newsList);

        List<NewsDto> actual = newsService.searchBy(searchCriteria);
        assertEquals(expectedNewsDtoList, actual);
        assertEquals(expectedNewsDtoList.get(0).getTitle(),actual.get(0).getTitle());
    }

    private void createInitNewsEntity() {
        news = new News();
        news.setId(1L);
        news.setTitle("TestTitle");
        news.setShortText("TestShortText");
        news.setFullText("TestFullText");
        LocalDate newsDate = LocalDate.now();
        news.setCreationDate(newsDate);
        news.setModificationDate(newsDate);

        List<News> authorNewsList = new ArrayList<>();
        Author author = new Author(1L, "name", "surname", authorNewsList);

        news.setAuthor(author);

        List<Tag> newsTags = new ArrayList<>();
        newsTags.add(new Tag("Tag"));
        news.setTags(newsTags);
    }

    private static NewsDto createNewsDto() {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(1L);
        newsDto.setTitle("TestTitle");
        newsDto.setShortText("TestShortText");
        newsDto.setFullText("TestFullText");
        LocalDate newsDtoDate = LocalDate.now();
        newsDto.setCreationDate(newsDtoDate);
        newsDto.setModificationDate(newsDtoDate);

        List<NewsDto> authorDtoNewsList = new ArrayList<>();
        AuthorDto authorDto = new AuthorDto(1L, "name", "surname", authorDtoNewsList);

        newsDto.setAuthor(authorDto);

        List<TagDto> newsDtoTags = new ArrayList<>();
        newsDtoTags.add(new TagDto("Tag"));
        newsDto.setTags(newsDtoTags);
        return newsDto;
    }
}
