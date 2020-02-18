package com.epam.lab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news", schema = "news")
public class News extends AbstractEntity {

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "short_text", length = 100)
    private String shortText;

    @Column(name = "full_text", length = 2000)
    private String fullText;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "modification_date")
    private LocalDate modificationDate;

    @ManyToOne
    private Author author;

    @OneToMany
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
