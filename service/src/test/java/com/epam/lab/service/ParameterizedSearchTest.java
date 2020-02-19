package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ParameterizedSearchTest {
    private static News news;

    private NewsService newsService;
    private NewsRepository newsRepository;

    private SearchCriteria searchCriteria;
    private List<NewsDto> expectedNewsDtos;

    public ParameterizedSearchTest(final SearchCriteria scValue, List<NewsDto> expectedNewsDtosValue) {
        searchCriteria = scValue;
        expectedNewsDtos = expectedNewsDtosValue;
    }

    @Parameterized.Parameters(name = "{index}: test with {0}, result = {1}")
    public static Collection<Object[]> data() {
        Set<String> tags = new HashSet<>();
        tags.add("Tag");

        Set<String> columns = new LinkedHashSet<>();
        columns.add("date");
        columns.add("tag_names");
        columns.add("srname");

        List<NewsDto> dtos = new ArrayList<>();
        dtos.add(createNewsDto());

        return Arrays.asList(
                new Object[][]{
                        {new SearchCriteria("name", "surname", tags, columns, true), dtos},
                        {new SearchCriteria("","surname", tags, columns,true), dtos},
                        {new SearchCriteria(null, "surname", tags, columns, true), dtos},
                        {new SearchCriteria("name", "", tags, columns,true), dtos},
                        {new SearchCriteria("name", null, tags, columns, true), dtos},
                        {new SearchCriteria("name", null, Collections.emptySet(), columns,true), dtos},
                        {new SearchCriteria("name", null, tags, Collections.emptySet(), true), dtos},
                        {new SearchCriteria(null, null, tags, columns, true), dtos},
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
        List<News> news = new ArrayList<>();
        news.add(ParameterizedSearchTest.news);

        when(newsRepository.searchBy(any(String.class))).thenReturn(news);

        List<NewsDto> actual = newsService.searchBy(searchCriteria);
        assertEquals(expectedNewsDtos, actual);
        assertEquals(expectedNewsDtos.get(0).getTitle(), actual.get(0).getTitle());

        verify(newsRepository).searchBy(any(String.class));
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

        Author author = new Author(1L, "name", "surname", new ArrayList<>());

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

        AuthorDto authorDto = new AuthorDto(1L, "name", "surname", new ArrayList<>());

        newsDto.setAuthor(authorDto);

        List<TagDto> newsDtoTags = new ArrayList<>();
        newsDtoTags.add(new TagDto("Tag"));
        newsDto.setTags(newsDtoTags);
        return newsDto;
    }
}
