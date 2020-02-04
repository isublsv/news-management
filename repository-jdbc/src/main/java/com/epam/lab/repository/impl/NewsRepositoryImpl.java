package com.epam.lab.repository.impl;

import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
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
            "SELECT title, short_text, full_text, creation_date, modification_date FROM news.news WHERE id=?;";

    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news.news WHERE id=?;";

    private static final String INSERT_NEWS_AND_AUTHOR_IDS =
            "INSERT INTO news.news_author (news_id, author_id) VALUES (?, ?);";

    private static final String INSERT_NEWS_AND_TAG_IDS = "INSERT INTO news.news_tag (news_id, tag_id) VALUES (?, ?);";

    private static final String SELECT_ALL_NEWS = "SELECT COUNT(*) FROM news.news;";

    private static final String SELECT_NEWS_BY_AUTHOR_ID = "SELECT news_id FROM news.news_author WHERE author_id=?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NewsRepositoryImpl(final JdbcTemplate jdbcTemplateValue) {
        this.jdbcTemplate = jdbcTemplateValue;
    }

    @Override
    public News create(final News entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEWS, new String[]{"id"});
            int counter = 1;
            ps.setString(counter++, entity.getTitle());
            ps.setString(counter++, entity.getShortText());
            ps.setString(counter++, entity.getFullText());
            ps.setDate(counter++, Date.valueOf(LocalDateTime.now().toLocalDate()));
            ps.setDate(counter, Date.valueOf(LocalDateTime.now().toLocalDate()));
            return ps;
        }, keyHolder);
        entity.setId(requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public News find(final Long id) {
        return jdbcTemplate.queryForObject(SELECT_NEWS_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(News.class));
    }

    @Override
    public News update(final News entity) {
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(UPDATE_NEWS_BY_ID, entity.getTitle(), entity.getShortText(), entity.getFullText(),
                now, entity.getId());
        entity.setModificationDate(now);
        return entity;
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update(DELETE_NEWS_BY_ID, id);
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
    public List<News> findNewsByAuthorId(final Long authorId) {
        return jdbcTemplate.query(SELECT_NEWS_BY_AUTHOR_ID, new Object[]{authorId}, (rs, rowNum) -> {
            News news = new News();
            news.setId(rs.getLong(1));
            return news;
        });
    }

    @Override
    public Long countAllNews() {
        return jdbcTemplate.queryForObject(SELECT_ALL_NEWS, Long.class);
    }
}
