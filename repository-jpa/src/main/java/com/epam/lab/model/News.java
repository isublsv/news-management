package com.epam.lab.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news", schema = "news")
public class News extends AbstractEntity {

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "short_text", length = 100, nullable = false)
    private String shortText;

    @Column(name = "full_text", length = 2000, nullable = false)
    private String fullText;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @Column(name = "modification_date", nullable = false)
    private LocalDate modificationDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade({org.hibernate.annotations.CascadeType.REPLICATE})
    @JoinTable(name = "news_author", schema = "news",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = "news_id"))
    private Author author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "news_tag", schema = "news",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"news_id", "tag_id"}))
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
