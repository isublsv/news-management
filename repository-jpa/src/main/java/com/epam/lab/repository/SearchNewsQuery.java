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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.lab.model.AbstractEntity_.ID;

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

        Set<String> tagNames = searchCriteria.getTags();
        if (isNotEmpty(tagNames)) {
            Expression<String> expression = newsRoot.join(News_.TAGS).get(Tag_.NAME);
            List<String> tags = tagNames.stream().map(String::toLowerCase).collect(Collectors.toList());
            Predicate predicate = expression.in(tags);
            predicates.add(predicate);
        }

        query.where(predicates.toArray(new Predicate[]{}))
             .groupBy(newsRoot.get(News_.AUTHOR).get(Author_.NAME), newsRoot.get(News_.AUTHOR).get(Author_.SURNAME),
                      newsRoot.get(News_.AUTHOR).get(ID), newsRoot)
             .having(builder.ge(builder.count(newsRoot), tagNames.size()));
    }

    private boolean isNotEmpty(final Set<String> value) {
        return !value.isEmpty();
    }

    private void getGroupsAndOrdersColumns(final Root<News> newsRootValue, final Join<News, Author> authorJoinValue) {
        List<Order> orders = new ArrayList<>();

        for (String columnValue : searchCriteria.getOrderBy()) {
            try {
                OrderBy order = OrderBy.valueOf(columnValue.toUpperCase());
                String columnName = order.getColumnName();
                switch (order) {
                    case DATE:
                        orders.add(builder.asc(newsRootValue.get(columnName)));
                        break;
                    case AUTHOR_NAME:
                    case AUTHOR_SURNAME:
                        orders.add(builder.asc(authorJoinValue.get(columnName)));
                        break;
                }
            } catch (IllegalArgumentException ignored) {
                return;
            }
        }

        query.orderBy(orders);
    }
}
