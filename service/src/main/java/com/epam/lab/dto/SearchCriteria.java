package com.epam.lab.dto;

import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria {

    @Pattern(regexp = "\\w+", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String name;

    @Pattern(regexp = "\\w+", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String surname;

    private Set<@Pattern(regexp = "\\w+", flags = Pattern.Flag.CASE_INSENSITIVE) String> tags;
    private Set<@Pattern(regexp = "\\w+", flags = Pattern.Flag.CASE_INSENSITIVE) String> orderBy;
    private boolean desc;

    public SearchCriteria() {
        tags = new HashSet<>();
        orderBy = new LinkedHashSet<>();
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

    @Override
    public String toString() {
        return String.format("SearchCriteria{name='%s', surname='%s', tags=%s, orderBy=%s, desc=%s}",
                name, surname, tags, orderBy, desc);
    }
}
