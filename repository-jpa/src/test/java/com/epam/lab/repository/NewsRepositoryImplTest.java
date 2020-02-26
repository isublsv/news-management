package com.epam.lab.repository;

import com.epam.lab.configuration.HibernateConfiguration;
import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class NewsRepositoryImplTest {

    @Autowired
    private NewsRepository newsRepository;

    @BeforeClass
    public static void setUp() {
        BasicConfigurator.configure();
    }

    @Test
    @Rollback
    public void shouldCreateNews() {
        News news = createNews();

        News expected = newsRepository.create(news);
        News actual = newsRepository.find(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void shouldCreateNewsWithExistingAuthor() {
        News news = createNews();

        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Sergei");
        existingAuthor.setSurname("Crachev");
        news.setAuthor(existingAuthor);

        News expected = newsRepository.create(news);
        News actual = newsRepository.find(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = EntityDuplicatedException.class)
    @Rollback
    public void shouldThrowExceptionWhenCreateNewsWithExistingTagName() {
        News news = createNews();
        news.addTag(new Tag("car"));

        newsRepository.create(news);
    }

    @Test
    public void shouldFindNewsById() {
        assertNotNull(newsRepository.find(1L));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionIfNewsWasNotFound() {
        newsRepository.find(Long.MAX_VALUE);
    }

    @Test
    @Rollback
    public void shouldUpdateNews() {
        News expected = newsRepository.find(1L);
        expected.setTitle("New Title");

        News actual = newsRepository.update(expected);

        assertEquals(expected, actual);
    }

    @Test(expected = EntityDuplicatedException.class)
    @Rollback
    public void shouldThrowExceptionWhenUpdateNewsWithExistingTags() {
        News expected = createNews();
        expected.setId(1L);
        expected.setTitle("New Title");
        expected.setCreationDate(LocalDate.of(2020, 1, 10));
        expected.addTag(new Tag("hockey"));

        News actual = newsRepository.update(expected);

        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    @Rollback
    public void shouldThrowExceptionWhenUpdateNewsWithNotExistingNewsId() {
        News news = createNews();
        news.setId(Long.MAX_VALUE);

        newsRepository.update(news);
    }

    @Test(expected = EntityNotFoundException.class)
    @Rollback
    public void shouldDeleteNewsById() {
        Long createdNewsId = newsRepository.create(createNews()).getId();
        assertNotNull(newsRepository.find(createdNewsId));
        newsRepository.delete(createdNewsId);
        newsRepository.find(createdNewsId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeleteNewsByNotExistingId() {
        newsRepository.delete(Long.MAX_VALUE);
    }

    @Test
    @Rollback
    public void shouldAddTagForNews() {
        long newsId = 1L;
        List<Tag> expectedTags = newsRepository.find(newsId).getTags();

        List<Tag> tagsToAdd = new ArrayList<>();
        tagsToAdd.add(new Tag("New Test Tag"));
        tagsToAdd.add(new Tag(7L, "hockey"));

        List<Tag> actualTags = newsRepository.addTagsForNews(newsId, tagsToAdd);

        assertEquals(expectedTags, actualTags);
    }

    @Test(expected = EntityNotFoundException.class)
    @Rollback
    public void shouldThrowExceptionWhenAddTagForNotExistingNews() {
        long newsId = Long.MAX_VALUE;
        newsRepository.addTagsForNews(newsId, new ArrayList<>());
    }

    @Test(expected = EntityDuplicatedException.class)
    @Rollback
    public void shouldThrowExceptionWhenAddTagForNewsIfTagExists() {
        long newsId = 1L;
        List<Tag> expectedTags = newsRepository.find(newsId).getTags();
        Tag tagWithExistingName = new Tag("car");
        expectedTags.add(tagWithExistingName);

        newsRepository.addTagsForNews(newsId, expectedTags);
    }

    @Test
    public void shouldCountOfAllNews() {
        Long actual = newsRepository.countAllNews();

        assertEquals(20L, actual.longValue());
    }

    private static News createNews() {
        News news = new News();
        news.setTitle("Test Title");
        news.setShortText("Test Short Text");
        news.setFullText("Test Full Text");
        LocalDate date = LocalDate.now();
        news.setCreationDate(date);
        news.setModificationDate(date);
        news.setAuthor(new Author("Test Name", "Test Surname"));
        news.addTag(new Tag("Test Tag"));
        news.addTag(new Tag(4L, "Car"));

        return news;
    }
}