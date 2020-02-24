package com.epam.lab.model;

public enum OrderBy {
    DATE(News_.CREATION_DATE),
    TAG_NAME(Tag_.NAME),
    AUTHOR_NAME(Author_.NAME),
    AUTHOR_SURNAME(Author_.SURNAME);

    private String columnName;

    OrderBy(final String columnNameValue) {
        columnName = columnNameValue;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
