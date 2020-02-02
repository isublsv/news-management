package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("author")
public class Author extends Entity {

    private String name;
    private String surname;
    private List<News> news;

    public Author() {
        super();
    }

    public Author(final String nameValue, final String surnameValue, final List<News> newsValue) {
        super();
        name = nameValue;
        surname = surnameValue;
        news = newsValue;
    }

    public Author(final long id, final String nameValue, final String surnameValue, final List<News> newsValue) {
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
    public List<News> getNews() {
        return news;
    }

    /**
     * Sets news.
     *
     * @param newsValue value of news.
     */
    public void setNews(final List<News> newsValue) {
        news = newsValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, news);
    }

    @Override
    public boolean equals(final Object oValue) {
        if (this == oValue) {
            return true;
        }
        if (oValue == null || getClass() != oValue.getClass()) {
            return false;
        }
        Author author = (Author) oValue;
        return name.equals(author.name) && surname.equals(author.surname) && Objects.equals(news, author.news);
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s', news='%s'}", getId(), name, surname,
                             news.toString());
    }
}
