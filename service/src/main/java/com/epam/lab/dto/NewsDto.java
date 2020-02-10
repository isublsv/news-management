package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component("newsDto")
public class NewsDto extends AbstractDto {

    private String title;
    private String shortText;
    private String fullText;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private AuthorDto author;
    private List<TagDto> tags;

    public NewsDto() {
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
     * Gets authorDto.
     *
     * @return value of authorDto.
     */
    public AuthorDto getAuthor() {
        return author;
    }

    /**
     * Sets authorDto.
     *
     * @param authorValue value of authorDto.
     */
    public void setAuthor(final AuthorDto authorValue) {
        author = authorValue;
    }

    /**
     * Gets tags.
     *
     * @return value of tags.
     */
    public List<TagDto> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tagsValue value of tags.
     */
    public void setTags(final List<TagDto> tagsValue) {
        tags = tagsValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, creationDate, modificationDate, author, tags);
    }

    @Override
    public boolean equals(final Object oValue) {
        if (this == oValue) {
            return true;
        }
        if (oValue == null || getClass() != oValue.getClass()) {
            return false;
        }
        NewsDto newsDto = (NewsDto) oValue;
        return title.equals(newsDto.title) && shortText.equals(newsDto.shortText) && fullText.equals(newsDto.fullText)
               && creationDate.equals(newsDto.creationDate) && modificationDate.equals(newsDto.modificationDate)
               && author.equals(newsDto.author) && Objects.equals(tags, newsDto.tags);
    }

    @Override
    public String toString() {
        return String.format("NewsDto{id=%d, title='%s', shortText='%s', fullText='%s', creationDate=%s,"
                + " modificationDate=%s, author=%s, tags=%s}",
                getId(), title, shortText, fullText, creationDate, modificationDate, author, tags);
    }
}
