package com.epam.lab.dto;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria {
    private String name;
    private String surname;
    private Set<String> tags;
    private Set<String> orderBy;
    private boolean desc;

    public SearchCriteria() {
        this.tags = new HashSet<>();
        this.orderBy = new LinkedHashSet<>();
    }

    public SearchCriteria(final String nameValue,
            final String surnameValue,
            final Set<String> tagsValue,
            final Set<String> orderByValue,
            final boolean descValue) {
        name = nameValue;
        surname = surnameValue;
        tags = tagsValue;
        orderBy = orderByValue;
        desc = descValue;
    }

    public String getName() {
        return name;
    }

    public void setName(final String nameValue) {
        name = nameValue;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surnameValue) {
        surname = surnameValue;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tagsValue) {
        tags = tagsValue;
    }

    public Set<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(final Set<String> orderByValue) {
        orderBy = orderByValue;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(final boolean descValue) {
        desc = descValue;
    }
}
