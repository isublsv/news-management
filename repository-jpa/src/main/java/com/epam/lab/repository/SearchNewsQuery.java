package com.epam.lab.repository;

import com.epam.lab.model.Author;
import com.epam.lab.model.Author_;
import com.epam.lab.model.News;
import com.epam.lab.model.News_;
import com.epam.lab.model.OrderBy;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

final class SearchNewsQuery {

    private CriteriaBuilder builder;
    private CriteriaQuery<News> query;
    private SearchCriteria searchCriteria;

    SearchNewsQuery(final CriteriaBuilder builderValue, final SearchCriteria searchCriteriaValue) {
        builder = builderValue;
        searchCriteria = searchCriteriaValue;
        query = builder.createQuery(News.class);
    }

    CriteriaQuery<News> buildQuery() {
        Root<News> newsRoot = query.from(News.class);
        Join<News, Author> authorJoin = newsRoot.join(News_.AUTHOR);

        getPredicates(newsRoot, authorJoin);
        getGroupsAndOrdersColumns(newsRoot, authorJoin);
        return query;
    }

    private void getPredicates(final Root<News> newsRoot, final Join<News, Author> authorJoin) {
        List<Predicate> predicates = new ArrayList<>();

        String name = searchCriteria.getName();
        if (name != null) {
            predicates.add(builder.equal(builder.upper(authorJoin.get(Author_.NAME)), name.toUpperCase()));
        }

        String surname = searchCriteria.getSurname();
        if (surname != null) {
            predicates.add(builder.equal(builder.upper(authorJoin.get(Author_.SURNAME)), surname.toUpperCase()));
        }

        Subquery<Long> tagSubquery = query.subquery(Long.class);
        Root<News> subRoot = tagSubquery.from(News.class);
        Join<Object, Object> subJoinTag = subRoot.join(News_.TAGS, JoinType.LEFT);

        for (String tagName : searchCriteria.getTags()) {
            tagSubquery.select(subRoot.get(News_.ID)).where(builder.equal(
                    builder.upper(subJoinTag.get(Tag_.NAME)), tagName.toUpperCase()));
            predicates.add(builder.equal(newsRoot.get(News_.ID), builder.any(tagSubquery)));
        }

        if (isNotEmpty(predicates)) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }
    }

    private boolean isNotEmpty(final List<Predicate> value) {
        return !value.isEmpty();
    }

    private void getGroupsAndOrdersColumns(final Root<News> newsRootValue, final Join<News, Author> authorJoinValue) {
        List<Order> orders = new ArrayList<>();
        List<Expression<?>> expressions = new ArrayList<>();
        expressions.add(newsRootValue.get(News_.ID));
        expressions.add(newsRootValue.get(News_.AUTHOR).get(Author_.ID));

        for (String columnValue : searchCriteria.getOrderBy()) {
            try {
                OrderBy order = OrderBy.valueOf(columnValue.toUpperCase());
                String columnName = order.getColumnName();
                switch (order) {
                    case DATE:
                        orders.add(builder.asc(newsRootValue.get(columnName)));
                        break;
                    case AUTHOR_NAME:
                        orders.add(builder.asc(authorJoinValue.get(columnName)));
                        expressions.add(authorJoinValue.get(Author_.NAME));
                        break;
                    case AUTHOR_SURNAME:
                        orders.add(builder.asc(authorJoinValue.get(columnName)));
                        expressions.add(authorJoinValue.get(Author_.SURNAME));
                        break;
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

        query.groupBy(expressions).orderBy(orders);
    }
}
