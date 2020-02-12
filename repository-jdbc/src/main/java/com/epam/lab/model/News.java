package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("news")
public class News extends Entity {

    private String title;
    private String shortText;
    private String fullText;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Author author;
    private List<Tag> tags;

    public News() {
        super();
    }

    public News(String title, String shortText, String fullText, LocalDateTime creationDate, LocalDateTime modificationDate, Author author, List<Tag> tags) {
        super();
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.author = author;
        this.tags = tags;
    }

    public News(long id, String title, String shortText, String fullText, LocalDateTime creationDate, LocalDateTime modificationDate, Author author, List<Tag> tags) {
        super(id);
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.author = author;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format("News{id=%d, title='%s', shortText='%s', fullText='%s', creationDate=%s, modificationDate=%s, author='%s', tags='%s'}", getId(),
                title, shortText, fullText, creationDate, modificationDate, author, tags);
    }
}
