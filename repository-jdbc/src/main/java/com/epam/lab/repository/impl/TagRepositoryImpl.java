package com.epam.lab.repository.impl;

import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
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
@Qualifier("tagRepository")
public class TagRepositoryImpl implements TagRepository {

    private static final String INSERT_TAG = "INSERT INTO news.tag (name) VALUES (?);";

    private static final String FIND_TAG_BY_ID = "SELECT name FROM news.tag WHERE id=?;";

    private static final String UPDATE_TAG_BY_ID = "UPDATE news.tag SET name=? WHERE id=?;";

    private static final String DELETE_TAG_BY_ID = "DELETE FROM news.tag WHERE id=?;";

    private static final String FIND_TAG_BY_ID_NAME = "SELECT id, name FROM news.tag WHERE id=? AND name=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(final JdbcTemplate jdbcTemplateValue) {
        this.jdbcTemplate = jdbcTemplateValue;
    }

    @Override
    public Tag create(final Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TAG, new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        entity.setId(requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Tag find(final long id) {
        return jdbcTemplate.queryForObject(FIND_TAG_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public void update(final Tag entity) {
        jdbcTemplate.update(UPDATE_TAG_BY_ID, entity.getName(), entity.getId());
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update(DELETE_TAG_BY_ID, id);
    }

    @Override
    public Tag findByTag(final Tag tag) {
        return jdbcTemplate.queryForObject(FIND_TAG_BY_ID_NAME,
                                           new Object[]{tag}, new BeanPropertyRowMapper<>(Tag.class));
    }
}
