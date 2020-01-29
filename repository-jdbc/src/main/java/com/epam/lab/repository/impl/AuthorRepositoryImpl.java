package com.epam.lab.repository.impl;

import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("authorRepository")
public class AuthorRepositoryImpl implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(final Author entity) {
        jdbcTemplate.update(
                "INSERT INTO news.author (name, surname) VALUES (?, ?);",
                entity.getName(), entity.getSurname());
    }

    @Override
    public Author find(final long id) {
        return jdbcTemplate.queryForObject(
                "SELECT name, surname FROM news.author WHERE id=?;",
                new Object[]{id}, new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public void update(final Author entity) {
        jdbcTemplate.update(
                "UPDATE news.author SET name=?, surname=? WHERE id=?;",
                entity.getName(), entity.getSurname(), entity.getId());
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update("DELETE FROM news.author WHERE id=?;", id);
    }
}
