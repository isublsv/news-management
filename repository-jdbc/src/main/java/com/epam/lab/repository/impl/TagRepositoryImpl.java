package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
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

    private static final String FIND_TAG_BY_ID = "SELECT id, name FROM news.tag WHERE id=?;";

    private static final String UPDATE_TAG_BY_ID = "UPDATE news.tag SET name=? WHERE id=?;";

    private static final String DELETE_TAG_BY_ID = "DELETE FROM news.tag WHERE id=?;";

    private static final String FIND_TAG_BY_ID_NAME = "SELECT id, name FROM news.tag WHERE id=? AND name=?;";

    private static final String FIND_TAG_BY_NAME = "SELECT EXISTS(SELECT id, name FROM news.tag WHERE name=?);";

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
    public Tag find(final Long id) {
        return jdbcTemplate.queryForObject(FIND_TAG_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag update(final Tag entity) {
        jdbcTemplate.update(UPDATE_TAG_BY_ID, entity.getName(), entity.getId());
        return entity;
    }

    @Override
    public void delete(final Long id) {
        int rowsNumber = jdbcTemplate.update(DELETE_TAG_BY_ID, id);
        if (rowsNumber == 0) {
            throw new RepositoryException("Tag " + id + " not found!");
        }
    }

    @Override
    public Tag findByTag(final Tag tag) {
        return jdbcTemplate.queryForObject(FIND_TAG_BY_ID_NAME,
                                           new Object[]{tag.getId(), tag.getName()}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Boolean findByTagName(final String name) {
        return jdbcTemplate.queryForObject(FIND_TAG_BY_NAME, new Object[]{name}, Boolean.class);
    }
}
