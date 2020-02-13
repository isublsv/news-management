package com.epam.lab.repository;

import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

@Repository
@Qualifier("newsRepository")
public class NewsRepositoryImpl implements NewsRepository {

    private static final String INSERT_NEWS = "INSERT INTO news.news (title, short_text, full_text, "
            + "creation_date, modification_date) VALUES (?, ?, ?, ?, ?);";

    private static final String UPDATE_NEWS_BY_ID =
            "UPDATE news.news SET title=?, short_text=?, full_text=?, modification_date=? WHERE id=?;";

    private static final String SELECT_NEWS_BY_ID =
            "SELECT news.id, title, short_text, full_text, creation_date, modification_date," 
            + " news.news_author.author_id FROM news.news LEFT JOIN news.news_author ON" 
            + " news.news.id=news.news_author.news_id WHERE news.id=?;";

    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news.news WHERE id=?;";

    private static final String INSERT_NEWS_AND_AUTHOR_IDS = "INSERT INTO news.news_author (news_id, author_id) "
            + "VALUES (?, ?);";
    private static final String REMOVE_AUTHOR_FOR_NEWS = "DELETE FROM news.news_author WHERE news_id=?;";

    private static final String INSERT_NEWS_AND_TAG_IDS = "INSERT INTO news.news_tag (news_id, tag_id) VALUES (?, ?);";

    private static final String SELECT_ALL_NEWS = "SELECT COUNT(*) FROM news.news;";

    private static final String SELECT_NEWS_BY_AUTHOR_ID = "SELECT news_id FROM news.news_author WHERE author_id=?;";

    private static final String FIND_NEWS_BY_TITLE = "SELECT EXISTS(SELECT title FROM news.news WHERE title=?);";

    private static final String FIND_BY_QUERY = "SELECT news_id, title, short_text, full_text, date, tag_ids,"
            + " tag_names, author_id, author_name, author_surname FROM news.full_view ";

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
        return jdbcTemplate.queryForObject(SELECT_NEWS_BY_ID, new Object[]{id}, (rs, rowNum) -> {
            News news = new News();
            news.setId(rs.getLong("id"));
            news.setTitle(rs.getString("title"));
            news.setShortText(rs.getString("short_text"));
            news.setFullText(rs.getString("full_text"));
            news.setCreationDate(rs.getDate("creation_date").toLocalDate());
            news.setModificationDate(rs.getDate("modification_date").toLocalDate());

            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            news.setAuthor(author);
            return news;
        });
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
        jdbcTemplate.update(DELETE_NEWS_BY_ID, id);
    }

    @Override
    public void addNewsAuthor(final Long newsId, final Long authorId) {
        jdbcTemplate.update(INSERT_NEWS_AND_AUTHOR_IDS, newsId, authorId);
    }

    @Override
    public void removeNewsAuthor(final Long newsId) {
        jdbcTemplate.update(REMOVE_AUTHOR_FOR_NEWS, newsId);
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
        return jdbcTemplate.queryForList(SELECT_NEWS_BY_AUTHOR_ID, new Object[]{authorId}, Long.class);
    }

    @Override
    public List<News> searchBy(final String sqlQuery) {
        String fullSearchQuery = FIND_BY_QUERY + sqlQuery;
        return jdbcTemplate.query(fullSearchQuery, (rs, rowNum) -> {
            News news = createNewsFromSearchQuery(rs);
            news.setTags(createNewsTagsFromSearchQuery(rs));
            news.setAuthor(createAuthorFromSearchQuery(rs));
            return news;
        });
    }

    @Override
    public Long countAllNews() {
        return jdbcTemplate.queryForObject(SELECT_ALL_NEWS, Long.class);
    }

    private News createNewsFromSearchQuery(final ResultSet rs) throws SQLException {
        News news = new News();
        news.setId(rs.getLong("news_id"));
        news.setTitle(rs.getString("title"));
        news.setShortText(rs.getString("short_text"));
        news.setFullText(rs.getString("full_text"));
        news.setCreationDate(rs.getDate("date").toLocalDate());
        return news;
    }

    private List<Tag> createNewsTagsFromSearchQuery(final ResultSet rs) throws SQLException {
        Array tagIds = rs.getArray("tag_ids");
        Array tagNames = rs.getArray("tag_names");
        List<Tag> tags = new ArrayList<>();
        if (tagIds != null && tagNames != null) {
            Long[] arrayIds = (Long[]) tagIds.getArray();
            String[] arrayNames = (String[]) tagNames.getArray();
            tags = IntStream.range(0, arrayIds.length)
                            .mapToObj(i -> new Tag(arrayIds[i], arrayNames[i]))
                            .collect(Collectors.toList());
        }
        return tags;
    }

    private Author createAuthorFromSearchQuery(final ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setName(rs.getString("author_name"));
        author.setSurname(rs.getString("author_surname"));
        return author;
    }
}
