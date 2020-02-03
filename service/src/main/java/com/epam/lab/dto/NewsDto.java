package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component("newsDto")
public class NewsDto extends AbstractDto {

    private String title;
    private String shortText;
    private String fullText;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private AuthorDto author;
    private boolean isAuthorNew;
    private List<TagDto> tags;
    private boolean isNewsFresh;

    public NewsDto() {
        super();
    }

    public NewsDto(final String titleValue,
            final String shortTextValue,
            final String fullTextValue,
            final LocalDateTime creationDateValue,
            final LocalDateTime modificationDateValue,
            final AuthorDto authorValue,
            final boolean isAuthorNewValue,
            final List<TagDto> tagsValue,
            final boolean isNewsFreshValue) {
        super();
        title = titleValue;
        shortText = shortTextValue;
        fullText = fullTextValue;
        creationDate = creationDateValue;
        modificationDate = modificationDateValue;
        author = authorValue;
        isAuthorNew = isAuthorNewValue;
        tags = tagsValue;
        isNewsFresh = isNewsFreshValue;
    }

    public NewsDto(final long id,
            final String titleValue,
            final String shortTextValue,
            final String fullTextValue,
            final LocalDateTime creationDateValue,
            final LocalDateTime modificationDateValue,
            final AuthorDto authorValue,
            final boolean isAuthorNewValue,
            final List<TagDto> tagsValue,
            final boolean isNewsFreshValue) {
        super(id);
        title = titleValue;
        shortText = shortTextValue;
        fullText = fullTextValue;
        creationDate = creationDateValue;
        modificationDate = modificationDateValue;
        author = authorValue;
        isAuthorNew = isAuthorNewValue;
        tags = tagsValue;
        isNewsFresh = isNewsFreshValue;
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
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creationDate.
     *
     * @param creationDateValue value of creationDate.
     */
    public void setCreationDate(final LocalDateTime creationDateValue) {
        creationDate = creationDateValue;
    }

    /**
     * Gets modificationDate.
     *
     * @return value of modificationDate.
     */
    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets modificationDate.
     *
     * @param modificationDateValue value of modificationDate.
     */
    public void setModificationDate(final LocalDateTime modificationDateValue) {
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
     * Gets isNewAuthor.
     *
     * @return value of isNewAuthor.
     */
    public boolean isAuthorNew() {
        return isAuthorNew;
    }

    /**
     * Sets isNewAuthor.
     *
     * @param authorNewValue value of isNewAuthor.
     */
    public void setAuthorNew(final boolean authorNewValue) {
        isAuthorNew = authorNewValue;
    }

    /**
     * Gets tagDtos.
     *
     * @return value of tagDtos.
     */
    public List<TagDto> getTags() {
        return tags;
    }

    /**
     * Sets tagDtos.
     *
     * @param tagsValue value of tagDtos.
     */
    public void setTags(final List<TagDto> tagsValue) {
        tags = tagsValue;
    }

    /**
     * Gets isNewsFresh.
     *
     * @return value of isNewsFresh.
     */
    public boolean isNewsFresh() {
        return isNewsFresh;
    }

    /**
     * Sets isNewsFresh.
     *
     * @param newsFreshValue value of isNewsFresh.
     */
    public void setNewsFresh(final boolean newsFreshValue) {
        isNewsFresh = newsFreshValue;
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
        return isAuthorNew == newsDto.isAuthorNew && isNewsFresh == newsDto.isNewsFresh && title.equals(newsDto.title)
               && shortText.equals(newsDto.shortText) && fullText.equals(newsDto.fullText) && creationDate.equals(
                newsDto.creationDate) && modificationDate.equals(newsDto.modificationDate) && author.equals(
                newsDto.author) && Objects.equals(tags, newsDto.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, creationDate, modificationDate, author, isAuthorNew, tags,
                            isNewsFresh);
    }
}
