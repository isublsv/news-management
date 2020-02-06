package com.epam.lab.repository.impl;

import com.epam.lab.configuration.DataSourceConfiguration;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
import static org.junit.Assert.assertNull;
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
    public void setUp() throws Exception {
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

    @Test
    public void shouldReturnNullIfIfNewsWasNotFound() {
        News actual = newsRepository.find(2L);

        assertNull(actual);
    }

    @Test
    @Rollback
    public void shouldUpdateNews() {
        News expected = createNewsForInsertion();
        News actual = newsRepository.update(expected);

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void shouldDeleteNewsById() {
        newsRepository.delete(1L);
        News actual = newsRepository.find(1L);
        assertNull(actual);
    }

    @Test(expected = RepositoryException.class)
    public void shouldThrowExceptionIfDeleteByNotExistingId() {
        newsRepository.delete(2L);
    }

    @Test
    @Rollback
    public void shouldAddAuthorForNews() {
        long newsId = 1L;
        long authorId = 1L;
        newsRepository.addNewsAuthor(newsId, authorId);
        News expected = newsRepository.find(newsId);
        List<Long> newsByAuthor = newsRepository.findNewsByAuthorId(authorId);

        assertEquals(1, newsByAuthor.size());
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

        assertEquals(1, newsTags.size());
        assertTrue(newsTags.contains(actual));
    }

    @Test
    public void shouldFindNewsByTitle() {
        String title = "Title";

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

        assertEquals(1L, actual.longValue());
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
}