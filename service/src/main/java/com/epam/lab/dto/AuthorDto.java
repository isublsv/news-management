package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("authorDto")
public class AuthorDto extends AbstractDto {

    private String name;
    private String surname;
    private List<NewsDto> newsDtos;

    public AuthorDto() {
        super();
    }

    public AuthorDto(Long id, String name, String surname, List<NewsDto> newsDtos) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.newsDtos = newsDtos;
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

    public List<NewsDto> getNews() {
        return newsDtos;
    }

    public void setNews(List<NewsDto> newsDtos) {
        this.newsDtos = newsDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(name, authorDto.name) &&
                Objects.equals(surname, authorDto.surname) &&
                newsDtos.equals(authorDto.newsDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, newsDtos);
    }
}
