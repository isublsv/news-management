package com.epam.lab.dto.mapper;

import com.epam.lab.dto.OrderBy;

import java.util.Set;

public final class SearchCriteriaBuilder {
    private StringBuilder builder;

    public SearchCriteriaBuilder() {
        builder = new StringBuilder(" WHERE (1=1) ");
    }
    
    public SearchCriteriaBuilder setAuthorName(final String authorName) {
        if (authorName != null && !authorName.isEmpty()) {
            builder.append(" AND (LOWER(author_name)='")
                   .append(authorName)
                   .append("') ");
        }
        return this;
    }
    
    public SearchCriteriaBuilder setAuthorSurname(final String authorSurname) {
        if (authorSurname != null && !authorSurname.isEmpty()) {
            builder.append(" AND (LOWER(author_surname)='")
                   .append(authorSurname)
                   .append("') ");
        }
        return this;
    }
    
    public SearchCriteriaBuilder setTags(final Set<String> tags) {
        tags.forEach(tagName -> builder.append(" AND ('")
                                       .append(tagName)
                                       .append("' = ANY(tag_names)) "));
        return this;
    }
    
    public SearchCriteriaBuilder setSortAndOrder(final Set<String> columns, final boolean order) {
        if (!columns.isEmpty()) {
            builder.append(" ORDER BY ");
            boolean isFirstColumn = false;
            for (String column : columns) {
                try {
                    if (!isFirstColumn) {
                        builder.append(OrderBy.valueOf(column.toUpperCase()).getColumnName());
                        isFirstColumn = true;
                    } else {
                        builder.append(", ").append(OrderBy.valueOf(column.toUpperCase()).getColumnName());
                    }
                } catch (IllegalArgumentException ignored) {
                }
            }
            if (order) {
                builder.append(" DESC");
            }
        }
        return this;
    }
    
    public String buildSearchQuery() {
        return builder.append(";").toString();
    }
}
