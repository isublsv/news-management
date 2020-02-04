package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("authorDto")
public class AuthorDto extends AbstractDto {

    private String name;
    private String surname;
    private List<NewsDto> news;

    public AuthorDto() {
        super();
    }

    public AuthorDto(final String nameValue, final String surnameValue, final List<NewsDto> newsValue) {
        super();
        name = nameValue;
        surname = surnameValue;
        news = newsValue;
    }

    public AuthorDto(final long id, final String nameValue, final String surnameValue,
            final List<NewsDto> newsValue) {
        super(id);
        name = nameValue;
        surname = surnameValue;
        news = newsValue;
    }

    /**
     * Gets name.
     *
     * @return value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param nameValue value of name.
     */
    public void setName(final String nameValue) {
        name = nameValue;
    }

    /**
     * Gets surname.
     *
     * @return value of surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surnameValue value of surname.
     */
    public void setSurname(final String surnameValue) {
        surname = surnameValue;
    }

    /**
     * Gets news.
     *
     * @return value of news.
     */
    public List<NewsDto> getNews() {
        return news;
    }

    /**
     * Sets news.
     *
     * @param newsValue value of news.
     */
    public void setNews(final List<NewsDto> newsValue) {
        news = newsValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(name, authorDto.name) && Objects.equals(surname, authorDto.surname) && news.equals(
                authorDto.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, news);
    }
}
