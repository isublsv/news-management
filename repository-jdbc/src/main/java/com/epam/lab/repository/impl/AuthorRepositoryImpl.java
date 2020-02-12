package com.epam.lab.repository.impl;

import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

import static java.util.Objects.requireNonNull;

@Repository
@Qualifier("authorRepository")
public class AuthorRepositoryImpl implements AuthorRepository {

    private static final String INSERT_AUTHOR = "INSERT INTO news.author (name, surname) VALUES (?, ?);";

    private static final String FIND_AUTHOR_BY_ID = "SELECT id, name, surname FROM news.author WHERE id=?;";

    private static final String UPDATE_AUTHOR_BY_ID = "UPDATE news.author SET name=?, surname=? WHERE id=?;";

    private static final String DELETE_AUTHOR_BY_ID = "DELETE FROM news.author WHERE id=?;";

    private static final String FIND_AUTHOR_BY_ID_NAME_SURNAME =
            "SELECT id, name, surname FROM news.author WHERE id=? AND name=? AND surname=?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryImpl(final JdbcTemplate jdbcTemplateValue) {
        this.jdbcTemplate = jdbcTemplateValue;
    }

    @Override
    public Author create(final Author entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_AUTHOR, new String[]{"id"});
            int counter = 1;
            ps.setString(counter++, entity.getName());
            ps.setString(counter, entity.getSurname());
            return ps;
        }, keyHolder);
        entity.setId(requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Author find(final Long id) {
        return jdbcTemplate.queryForObject(FIND_AUTHOR_BY_ID,
                new Object[]{id}, new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public Author update(final Author entity) {
        jdbcTemplate.update(UPDATE_AUTHOR_BY_ID, entity.getName(), entity.getSurname(), entity.getId());
        return entity;
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update(DELETE_AUTHOR_BY_ID, id);
    }

    @Override
    public Author findByAuthor(final Author author) {
        return jdbcTemplate.queryForObject(FIND_AUTHOR_BY_ID_NAME_SURNAME,
                                           new Object[]{author.getId(), author.getName(), author.getSurname()},
                                           new BeanPropertyRowMapper<>(Author.class));
    }
}
