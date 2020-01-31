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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Repository
@Qualifier("newsRepository")
public class NewsRepositoryImpl implements NewsRepository {

    private final JdbcTemplate jdbcTemplate;

    public static final String INSERT_NEWS = "INSERT INTO news.news (title, short_text, full_text, "
            + "creation_date, modification_date) VALUES (?, ?, ?, ?, ?);";

    @Autowired
    public NewsRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public News create(News entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_NEWS, new String[]{"id"});
                    int counter = 1;
                    ps.setString(counter++, entity.getTitle());
                    ps.setString(counter++, entity.getShortText());
                    ps.setString(counter++, entity.getFullText());
                    ps.setDate(counter++, Date.valueOf(LocalDateTime.now().toLocalDate()));
                    ps.setDate(counter, Date.valueOf(LocalDateTime.now().toLocalDate()));
                    return ps;
                },
                keyHolder);
        entity.setId(requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public News find(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT title, short_text, full_text, creation_date, "
                        + "modification_date FROM news.news WHERE id=?;",
                new Object[]{id}, new BeanPropertyRowMapper<>(News.class));
    }

    @Override
    public void update(News entity) {
        jdbcTemplate.update(
                "UPDATE news.news SET title=?, short_text=?, full_text=?, "
                        + "modification_date=? WHERE id=?;", entity.getTitle(),
                entity.getShortText(), entity.getFullText(),
                LocalDateTime.now(), entity.getId());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM news.news WHERE id=?;", id);
    }

    @Override
    public void addNewsAuthor(long newsId, long authorId) {
        jdbcTemplate.update("INSERT INTO news.news_author (news_id, author_id);", newsId, authorId);
    }

    @Override
    public Long countAllNews() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM news.news;", Long.class);
    }


}
