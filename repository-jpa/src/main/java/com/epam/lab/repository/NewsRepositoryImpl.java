package com.epam.lab.repository;

import com.epam.lab.model.AbstractEntity;
import com.epam.lab.model.Author;
import com.epam.lab.model.Author_;
import com.epam.lab.model.News;
import com.epam.lab.model.News_;
import com.epam.lab.model.OrderBy;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import com.epam.lab.model.Tag_;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public News create(final News entity) {
        LocalDate date = LocalDate.now();
        entity.setCreationDate(date);
        entity.setModificationDate(date);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public News find(final Long id) {
        return entityManager.find(News.class, id);
    }

    @Override
    public News update(final News entity) {
        News news = find(entity.getId());

        news.setTitle(entity.getTitle());
        news.setShortText(entity.getShortText());
        news.setFullText(entity.getFullText());
        news.setModificationDate(LocalDate.now());

        Long authorId = entity.getAuthor().getId();
        if (authorId != null) {
            Author author = entityManager.find(Author.class, authorId);
            news.setAuthor(author);
        } else {
            news.setAuthor(entity.getAuthor());
        }
        List<Tag> tags = news.getTags();
        tags.clear();
        tags.addAll(entity.getTags());

        return entityManager.merge(news);
    }

    @Override
    public void delete(final Long id) {
        News news = find(id);
        if (news != null) {
            entityManager.remove(news);
        }
    }

    @Override
    public Long countAllNews() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> root = criteriaQuery.from(News.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<News> searchBy(final SearchCriteria searchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = builder.createQuery(News.class);
        Root<News> newsRoot = query.from(News.class);
        Join<News, Author> authorJoin = newsRoot.join(News_.AUTHOR);
        Join<News, Tag> tagJoin = newsRoot.join(News_.TAGS, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        String name = searchCriteria.getName();
        if (name != null) {
            predicates.add(builder.equal(builder.upper(authorJoin.get(Author_.NAME)), name.toUpperCase()));
        }

        String surname = searchCriteria.getSurname();
        if (surname != null) {
            predicates.add(builder.equal(builder.upper(authorJoin.get(Author_.SURNAME)), surname.toUpperCase()));
        }

        searchCriteria.getTags().forEach(tagName -> predicates
                .add( builder.equal(builder.upper(tagJoin.get(Tag_.NAME)), tagName.toUpperCase())));

        List<Order> orders = new ArrayList<>();

        for (String column : searchCriteria.getOrderBy()) {
            orders.add(builder.asc(getOrder(column, newsRoot)));
        }

        if (isNotEmpty(predicates)) {
            //faster than .toArray(new Predicate[predicates.size()]
            //https://stackoverflow.com/questions/174093/toarraynew-myclass0-or-toarraynew-myclassmylist-size
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }
        if (isNotEmpty(orders)) {
            query.orderBy(orders);
        }
        return entityManager.createQuery(query).getResultList();
    }

    private boolean isNotEmpty(final List<?> value) {
        return !value.isEmpty();
    }

    public <T extends AbstractEntity> Path<T> getOrder(final String column, final Root<T> root) {
        try {
            return root.get(OrderBy.valueOf(column.toUpperCase()).getColumnName());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public <T extends AbstractEntity, E extends AbstractEntity> Path<T> getOrder(final String column,
            final Join<T, E> join) {
        try {
            return join.get(OrderBy.valueOf(column.toUpperCase()).getColumnName());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
