package com.epam.lab.dto;

import java.util.HashSet;
import java.util.Set;

public class SearchCriteria {
    private String name;
    private String surname;
    private Set<String> tags;
    private String orderBy;
    private boolean desc;

    public SearchCriteria() {
        this.tags = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tagsList) {
        this.tags = tagsList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(final String orderByParameter) {
        this.orderBy = orderByParameter;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(final boolean descValue) {
        desc = descValue;
    }

    public String accept() {
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE (1=1) ");
        if (name != null && !name.isEmpty()) {
            builder.append(" AND (author.name=").append(name).append(") ");
        }
        if (surname != null && !surname.isEmpty()) {
            builder.append(" AND (author.surname=").append(surname).append(") ");
        }
        tags.forEach(c -> builder.append(" AND (").append(c).append(" = ANY(tag_names)) "));
        if (orderBy != null && !orderBy.isEmpty()) {
            builder.append(" ORDER BY ").append(orderBy);
        }
        if (desc) {
            builder.append(" DESC");
        }
        return builder.append(";").toString();
    }
}