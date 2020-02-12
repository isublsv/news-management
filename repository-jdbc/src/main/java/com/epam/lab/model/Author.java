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

    public Author(final Long id, final String nameValue, final String surnameValue, final List<News> newsValue) {
        super(id);
        name = nameValue;
        surname = surnameValue;
        news = newsValue;
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(final List<News> newsValue) {
        news = newsValue;
    }

    @Override
    public boolean equals(Object oValue) {
        if (this == oValue) return true;
        if (oValue == null || getClass() != oValue.getClass()) return false;
        Author author = (Author) oValue;
        return Objects.equals(name, author.name) &&
                Objects.equals(surname, author.surname) &&
                Objects.equals(news, author.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, news);
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s', news='%s'}", getId(), name, surname, news);
    }
}
