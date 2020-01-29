package com.epam.lab.repository.impl;

import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Qualifier("newsRepository")
public class NewsRepositoryImpl implements NewsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NewsRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(News entity) {
        jdbcTemplate.update(
                "INSERT INTO news.news (title, short_text, full_text, " 
                + "creation_date, modification_date) VALUES (?, ?, ?, ?, ?);",
                entity.getTitle(), entity.getShortText(), entity.getFullText(),
                entity.getCreationDate(), entity.getModificationDate());
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
}
