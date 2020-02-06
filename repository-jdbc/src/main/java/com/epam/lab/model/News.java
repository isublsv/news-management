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

    /**
     * Gets title.
     *
     * @return value of title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param titleValue value of title.
     */
    public void setTitle(final String titleValue) {
        title = titleValue;
    }

    /**
     * Gets shortText.
     *
     * @return value of shortText.
     */
    public String getShortText() {
        return shortText;
    }

    /**
     * Sets shortText.
     *
     * @param shortTextValue value of shortText.
     */
    public void setShortText(final String shortTextValue) {
        shortText = shortTextValue;
    }

    /**
     * Gets fullText.
     *
     * @return value of fullText.
     */
    public String getFullText() {
        return fullText;
    }

    /**
     * Sets fullText.
     *
     * @param fullTextValue value of fullText.
     */
    public void setFullText(final String fullTextValue) {
        fullText = fullTextValue;
    }

    /**
     * Gets creationDate.
     *
     * @return value of creationDate.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creationDate.
     *
     * @param creationDateValue value of creationDate.
     */
    public void setCreationDate(final LocalDate creationDateValue) {
        creationDate = creationDateValue;
    }

    /**
     * Gets modificationDate.
     *
     * @return value of modificationDate.
     */
    public LocalDate getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets modificationDate.
     *
     * @param modificationDateValue value of modificationDate.
     */
    public void setModificationDate(final LocalDate modificationDateValue) {
        modificationDate = modificationDateValue;
    }

    /**
     * Gets author.
     *
     * @return value of author.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param authorValue value of author.
     */
    public void setAuthor(final Author authorValue) {
        author = authorValue;
    }

    /**
     * Gets tags.
     *
     * @return value of tags.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tagsValue value of tags.
     */
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
