package com.epam.lab.dto;

public enum OrderBy {
    DATE("date"),
    TAG_NAMES("tag_names"),
    AUTHOR_NAME("author_name"),
    AUTHOR_SURNAME("author_surname");

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
