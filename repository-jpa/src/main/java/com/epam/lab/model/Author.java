package com.epam.lab.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "news")
public class Author extends AbstractEntity {

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<News> news = new ArrayList<>();

    public Author() {
        super();
    }

    public Author(final String nameValue, final String surnameValue) {
        super();
        name = nameValue;
        surname = surnameValue;
    }

    public Author(final Long id, final String nameValue, final String surnameValue) {
        super(id);
        name = nameValue;
        surname = surnameValue;
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

    public void addNews(News newsValue) {
        news.add(newsValue);
        newsValue.setAuthor(this);
    }

    public void removeNews(News newsValue) {
        news.remove(newsValue);
        newsValue.setAuthor(null);
    }

    @Override
    public boolean equals(Object oValue) {
        if (this == oValue) return true;
        if (oValue == null || getClass() != oValue.getClass()) return false;
        Author author = (Author) oValue;
        return Objects.equals(name, author.name) && Objects.equals(surname, author.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s'}", getId(), name, surname);
    }
}
