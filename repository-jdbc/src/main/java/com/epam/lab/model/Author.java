package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("author")
public class Author extends Entity{

    private String name;
    private String surname;
    private List<News> news;

    public Author() {
        super();
    }

    public Author(String name, String surname, List<News> news) {
        super();
        this.name = name;
        this.surname = surname;
        this.news = news;
    }

    public Author(long id, String name, String surname, List<News> news) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s', surname='%s', news='%s'}", getId(), name, surname, news.toString());
    }
}
