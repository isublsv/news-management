package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
@Qualifier("newsRepository")
public class NewsRepositoryImpl implements NewsRepository {

    private static final String INSERT_NEWS = "INSERT INTO news.news (title, short_text, full_text, "
            + "creation_date, modification_date) VALUES (?, ?, ?, ?, ?);";

    private static final String UPDATE_NEWS_BY_ID =
            "UPDATE news.news SET title=?, short_text=?, full_text=?, modification_date=? WHERE id=?;";

    private static final String SELECT_NEWS_BY_ID =
            "SELECT news.id, title, short_text, full_text, creation_date, modification_date, news.news_author.author_id"
                    + " FROM news.news LEFT JOIN news.news_author WHERE news.id=?;";

    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news.news WHERE id=?;";

    private static final String INSERT_NEWS_AND_AUTHOR_IDS = "INSERT INTO news.news_author (news_id, author_id) "
                                                             + "VALUES (?, ?);";

    private static final String INSERT_NEWS_AND_TAG_IDS = "INSERT INTO news.news_tag (news_id, tag_id) VALUES (?, ?);";

    private static final String SELECT_ALL_NEWS = "SELECT COUNT(*) FROM news.news;";

    private static final String SELECT_NEWS_BY_AUTHOR_ID = "SELECT news_id FROM news.news_author WHERE author_id=?;";

    private static final String FIND_NEWS_BY_TITLE = "SELECT EXISTS(SELECT title FROM news.news WHERE title=?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NewsRepositoryImpl(final JdbcTemplate jdbcTemplateValue) {
        this.jdbcTemplate = jdbcTemplateValue;
    }

    @Override
    public News create(final News entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDate dateTime = LocalDate.now();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEWS, new String[]{"id"});
            int counter = 1;
            ps.setString(counter++, entity.getTitle());
            ps.setString(counter++, entity.getShortText());
            ps.setString(counter++, entity.getFullText());
            ps.setDate(counter++, Date.valueOf(dateTime));
            ps.setDate(counter, Date.valueOf(dateTime));
            return ps;
        }, keyHolder);
        entity.setId(requireNonNull(keyHolder.getKey()).longValue());
        entity.setCreationDate(dateTime);
        entity.setModificationDate(dateTime);
        return entity;
    }

    @Override
    public News find(final Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_NEWS_BY_ID, new Object[]{id}, (rs, rowNum) -> {
                News news = new News();
                int counter = 1;
                news.setId(rs.getLong(counter++));
                news.setTitle(rs.getString(counter++));
                news.setShortText(rs.getString(counter++));
                news.setFullText(rs.getString(counter++));
                news.setCreationDate(rs.getDate(counter++).toLocalDate());
                news.setModificationDate(rs.getDate(counter++).toLocalDate());

                Author author = new Author();
                author.setId(rs.getLong(counter));
                news.setAuthor(author);
                return news;
            });
        } catch (EmptyResultDataAccessException e) {
            //TODO log
            return null;
        }
    }

    @Override
    public News update(final News entity) {
        LocalDate now = LocalDate.now();
        jdbcTemplate.update(UPDATE_NEWS_BY_ID, entity.getTitle(), entity.getShortText(), entity.getFullText(),
                now, entity.getId());
        entity.setModificationDate(now);
        return entity;
    }

    @Override
    public void delete(final Long id) {
        int rowNumber = jdbcTemplate.update(DELETE_NEWS_BY_ID, id);
        if (rowNumber == 0) {
            throw new RepositoryException("News with " + id + " not found!");
        }
    }

    @Override
    public void addNewsAuthor(final Long newsId, final Long authorId) {
        jdbcTemplate.update(INSERT_NEWS_AND_AUTHOR_IDS, newsId, authorId);
    }

    @Override
    public void addNewsTag(final Long newsId, final Long tagId) {
        jdbcTemplate.update(INSERT_NEWS_AND_TAG_IDS, newsId, tagId);
    }

    @Override
    public Boolean findNewsByTitle(final String title) {
        return jdbcTemplate.queryForObject(FIND_NEWS_BY_TITLE, Boolean.class, title);
    }

    @Override
    public List<Long> findNewsByAuthorId(final Long authorId) {
        return jdbcTemplate.query(SELECT_NEWS_BY_AUTHOR_ID, new Object[]{authorId}, new BeanPropertyRowMapper<>(Long.class));
    }

    @Override
    public List<News> searchBy(final String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(News.class));
    }

    @Override
    public Long countAllNews() {
        return jdbcTemplate.queryForObject(SELECT_ALL_NEWS, Long.class);
    }
}
