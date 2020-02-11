package com.epam.lab.dto;

import java.util.Set;

public final class SearchCriteriaBuilder {

    private static final String START_CONDITION = " WHERE (1=1) ";
    private static final String AUTHOR_NAME_CONDITION = " AND (LOWER(author_name)='";
    private static final String AUTHOR_SURNAME_CONDITION = " AND (LOWER(author_surname)='";
    private static final String CLOSED_PARENTHESIS = "') ";
    private static final String OPEN_BRACKET = " AND ('";
    private static final String TAG_NAME_CONDITION = "' = ANY(tag_names)) ";
    private static final String FIRST_COLUMN_ORDER_CONDITION = " ORDER BY ";
    private static final String COMMA_SEPARATOR = ", ";
    private static final String SORT_ORDER_CONDITION = " DESC";
    private static final String SEMICOLON = ";";

    private StringBuilder builder;

    public SearchCriteriaBuilder() {
        builder = new StringBuilder(START_CONDITION);
    }

    public SearchCriteriaBuilder setAuthorName(final String authorName) {
        if (isValueExist(authorName)) {
            builder.append(AUTHOR_NAME_CONDITION).append(authorName).append(CLOSED_PARENTHESIS);
        }
        return this;
    }

    public SearchCriteriaBuilder setAuthorSurname(final String authorSurname) {
        if (isValueExist(authorSurname)) {
            builder.append(AUTHOR_SURNAME_CONDITION).append(authorSurname).append(CLOSED_PARENTHESIS);
        }
        return this;
    }

    public SearchCriteriaBuilder setTags(final Set<String> tags) {
        tags.forEach(tagName -> builder.append(OPEN_BRACKET).append(tagName).append(TAG_NAME_CONDITION));
        return this;
    }
    
    public SearchCriteriaBuilder setSortAndOrder(final Set<String> columns, final boolean order) {
        boolean isFirstColumn = false;
        for (String column : columns) {
            try {
                OrderBy columnOrder = OrderBy.valueOf(column.toUpperCase());
                if (!isFirstColumn) {
                    builder.append(FIRST_COLUMN_ORDER_CONDITION).append(columnOrder.getColumnName());
                    isFirstColumn = true;
                } else {
                    builder.append(COMMA_SEPARATOR).append(columnOrder.getColumnName());
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (order && isFirstColumn) {
            builder.append(SORT_ORDER_CONDITION);
        }
        return this;
    }

    public String buildSearchQuery() {
        return builder.append(SEMICOLON).toString();
    }

    private boolean isValueExist(final String authorName) {
        return authorName != null && !authorName.isEmpty();
    }
}
