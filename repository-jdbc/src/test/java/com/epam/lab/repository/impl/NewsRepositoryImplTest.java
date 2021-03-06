package com.epam.lab.repository.impl;

import com.epam.lab.configuration.DataSourceConfiguration;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.NewsRepositoryImpl;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.TagRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class NewsRepositoryImplTest {

    private NewsRepository newsRepository;
    private TagRepository tagRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        newsRepository = new NewsRepositoryImpl(jdbcTemplate);
        tagRepository = new TagRepositoryImpl(jdbcTemplate);
    }

    @Test
    @Rollback
    public void shouldCreateNews() {
        News news = createNewsForInsertion();
        News expected = newsRepository.create(news);
        News actual = newsRepository.find(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionIfIfNewsWasNotFound() {
        newsRepository.find(21L);
    }

    @Test
    @Rollback
    public void shouldUpdateNews() {
        News expected = createNewsForInsertion();
        News actual = newsRepository.update(expected);

        assertEquals(expected, actual);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Rollback
    public void shouldDeleteNewsById() {
        Long createdNewsId = newsRepository.create(createNewsForInsertion()).getId();
        assertNotNull(newsRepository.find(createdNewsId));
        newsRepository.delete(createdNewsId);
        newsRepository.find(createdNewsId);
    }

    @Test
    @Rollback
    public void shouldAddAuthorForNews() {
        long newsId = 20L;
        long authorId = 4L;
        newsRepository.addNewsAuthor(newsId, authorId);
        News expected = newsRepository.find(newsId);
        List<Long> newsByAuthor = newsRepository.findNewsByAuthorId(authorId);

        assertEquals(4, newsByAuthor.size());
        assertTrue(newsByAuthor.contains(expected.getId()));
    }

    @Test
    @Rollback
    public void shouldAddTagForNews() {
        long newsId = 1L;
        long tagId = 1L;
        newsRepository.addNewsTag(newsId, tagId);
        List<Tag> newsTags = tagRepository.findTagsByNewsId(newsId);
        Tag actual = tagRepository.find(tagId);

        assertEquals(4, newsTags.size());
        assertTrue(newsTags.contains(actual));
    }

    @Test
    public void shouldFindNewsByTitle() {
        String title = "title 1";

        assertTrue(newsRepository.findNewsByTitle(title));
    }

    @Test
    public void shouldFailedToFindNewsByTitle() {
        String title = "Test Title";

        assertFalse(newsRepository.findNewsByTitle(title));
    }

    @Test
    public void shouldCountOfAllNews() {
        Long actual = newsRepository.countAllNews();

        assertEquals(20L, actual.longValue());
    }

    private static News createNewsForInsertion() {
        News news = new News();
        news.setTitle("Test Title");
        news.setShortText("Test Short Text");
        news.setFullText("Test Full Text");
        LocalDate date = LocalDate.now();
        news.setCreationDate(date);
        news.setModificationDate(date);
        news.setAuthor(new Author());

        return news;
    }

    @Test
    public void shouldSearchNewsByProvidedQuery() {
        String initSqlQuery = " WHERE (1=1)  AND (LOWER(author_name)='Igor')" +
                " AND (LOWER(author_surname)='Bikov')";
        List<News> actual = newsRepository.searchBy(initSqlQuery);

        assertEquals(0, actual.size());
    }
}
