package com.epam.lab.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SearchCriteriaDto extends AbstractDto {

    @Length(min = 2, max = 30, message = "The author name length must be between 2 and 30 characters.")
    @Pattern(regexp = "^[A-ZА-Я]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided name is not valid.")
    private String name;

    @Length(min = 2, max = 30, message = "The author surname length must be between 2 and 30 characters.")
    @Pattern(regexp = "^[A-ZА-Я\\-]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided surname is not valid.")
    private String surname;

    private Set<
            @Valid
            @Length(min = 2, max = 30, message = "The tag name length must be between 2 and 30 characters.")
            @Pattern(regexp = "^[A-ZА-Я_!?\\-\\d ]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided tag name is not valid.") String> tags;

    private Set<
            @Valid
            @Pattern(regexp = "^[A-ZА-Я_]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided column name is not valid.") String> orderBy;

    public SearchCriteriaDto() {
        tags = new HashSet<>();
        orderBy = new LinkedHashSet<>();
    }

    public SearchCriteriaDto(final String nameValue,
                             final String surnameValue,
                             final Set<String> tagsValue,
                             final Set<String> orderByValue) {
        name = nameValue;
        surname = surnameValue;
        tags = tagsValue;
        orderBy = orderByValue;
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

    @Override
    public String toString() {
        return String.format("SearchCriteria{name='%s', surname='%s', tags=%s, orderBy=%s}",
                name, surname, tags, orderBy);
    }
}
