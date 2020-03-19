package com.epam.lab.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria extends AbstractEntity {

    private String name;
    private String surname;
    private Set<String> tags = new HashSet<>();
    private Set<String> orderBy = new LinkedHashSet<>();

    private int activePage;
    private int pageSize;

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

    public int getActivePage() {
        return activePage;
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return String.format("SearchCriteria{name='%s', surname='%s', tags=%s, orderBy=%s}", name, surname,
                tags, orderBy);
    }
}
