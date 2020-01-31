package com.epam.lab.repository.impl;

import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("tagRepository")
public class TagRepositoryImpl implements TagRepository {

    public static final String INSERT_TAG = "INSERT INTO news.tag (name) VALUES (?);";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(final Tag entity) {
        jdbcTemplate.update(INSERT_TAG, entity.getName());
        return entity;
    }

    @Override
    public Tag find(final long id) {
        return jdbcTemplate.queryForObject(
                "SELECT name FROM news.tag WHERE id=?;", new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public void update(final Tag entity) {
        jdbcTemplate.update("UPDATE news.tag SET name=? WHERE id=?;",
                            entity.getName(), entity.getId());
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update("DELETE FROM news.tag WHERE id=?;", id);
    }

    @Override
    public Tag findByTag(Tag tag) {
        return jdbcTemplate.queryForObject("SELECT id, name FROM news.tag WHERE id=? AND name=?",
                new Object[]{tag}, new BeanPropertyRowMapper<>(Tag.class));
    }
}
