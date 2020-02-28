package com.epam.lab.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria extends AbstractEntity {

    private String name;
    private String surname;
    private Set<String> tags = new HashSet<>();
    private Set<String> orderBy = new LinkedHashSet<>();

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

    @Override
    public String toString() {
        return String.format("SearchCriteria{name='%s', surname='%s', tags=%s, orderBy=%s}", name, surname,
                tags, orderBy);
    }
}
