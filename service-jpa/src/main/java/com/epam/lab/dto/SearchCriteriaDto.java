package com.epam.lab.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteriaDto extends AbstractDto{

    @Length(min = 2, max = 30, message = "The author name length must be between 2 and 30 characters")
    @Pattern(regexp = "[A-ZА-Я]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided name is not valid")
    private String name;

    @Length(min = 2, max = 30, message = "The author surname length must be between 2 and 30 characters")
    @Pattern(regexp = "[A-ZА-Я\\-]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided surname is not valid")
    private String surname;

    @Length(min = 2, max = 30, message = "The tag name length must be between 2 and 30 characters")
    private Set<@Pattern(regexp = "[A-ZА-Я_!?\\-\\d ]{2,30}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided tag name is not valid") String> tags;

    private Set<@Pattern(regexp = "[A-ZА-Я_]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided column name is not valid") String> orderBy;

    private boolean desc;

    public SearchCriteriaDto() {
        tags = new HashSet<>();
        orderBy = new LinkedHashSet<>();
    }

    public SearchCriteriaDto(final String nameValue,
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
