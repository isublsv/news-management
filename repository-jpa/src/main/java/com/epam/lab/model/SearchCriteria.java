package com.epam.lab.model;

import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteria extends AbstractEntity{

    @Pattern(regexp = "[A-ZА-Я]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided name is not valid")
    private String name;

    @Pattern(regexp = "[A-ZА-Я\\-]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided surname is not valid")
    private String surname;

    private Set<@Pattern(regexp = "[A-ZА-Я\\-\\d ]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided tag name is not valid") String> tags = new HashSet<>();

    private Set<@Pattern(regexp = "[A-ZА-Я\\-\\d ]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided column name is not valid") String> orderBy = new LinkedHashSet<>();
    
    private boolean desc;

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
        return String.format("SearchCriteria{name='%s', surname='%s', tags=%s, orderBy=%s, desc=%s}", name, surname,
                             tags, orderBy, desc);
    }
}
