package com.epam.lab.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component("news")
public class News extends Entity {

    private String title;
    private String shortText;
    private String fullText;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private Author author;
    private List<Tag> tags;

    public News() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String titleValue) {
        title = titleValue;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(final String shortTextValue) {
        shortText = shortTextValue;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(final String fullTextValue) {
        fullText = fullTextValue;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final LocalDate creationDateValue) {
        creationDate = creationDateValue;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(final LocalDate modificationDateValue) {
        modificationDate = modificationDateValue;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(final Author authorValue) {
        author = authorValue;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tagsValue) {
        tags = tagsValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, creationDate, modificationDate, author, tags);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        return title.equals(news.title) && shortText.equals(news.shortText) && fullText.equals(news.fullText)
               && creationDate.equals(news.creationDate) && modificationDate.equals(news.modificationDate)
               && author.equals(news.author) && Objects.equals(tags, news.tags);
    }

    @Override
    public String toString() {
        return String.format(
                "News{id=%d, title='%s', shortText='%s', fullText='%s', creationDate=%s, modificationDate=%s, "
                + "author='%s', tags='%s'}", getId(), title, shortText, fullText, creationDate, modificationDate,
                author, tags);
    }
}
