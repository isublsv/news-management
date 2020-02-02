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
    private AuthorDto authorDto;
    private boolean isAuthorNew;
    private List<TagDto> tagDtos;
    private boolean isNewsFresh;

    public NewsDto() {
        super();
    }

    public NewsDto(final String titleValue, final String shortTextValue, final String fullTextValue,
            final LocalDateTime creationDateValue, final LocalDateTime modificationDateValue,
            final AuthorDto authorDtoValue, final boolean isAuthorNewValue, final List<TagDto> tagDtosValue,
            final boolean isNewsFreshValue) {
        super();
        title = titleValue;
        shortText = shortTextValue;
        fullText = fullTextValue;
        creationDate = creationDateValue;
        modificationDate = modificationDateValue;
        authorDto = authorDtoValue;
        isAuthorNew = isAuthorNewValue;
        tagDtos = tagDtosValue;
        isNewsFresh = isNewsFreshValue;
    }

    public NewsDto(final long id, final String titleValue, final String shortTextValue, final String fullTextValue,
            final LocalDateTime creationDateValue, final LocalDateTime modificationDateValue,
            final AuthorDto authorDtoValue, final boolean isAuthorNewValue, final List<TagDto> tagDtosValue,
            final boolean isNewsFreshValue) {
        super(id);
        title = titleValue;
        shortText = shortTextValue;
        fullText = fullTextValue;
        creationDate = creationDateValue;
        modificationDate = modificationDateValue;
        authorDto = authorDtoValue;
        isAuthorNew = isAuthorNewValue;
        tagDtos = tagDtosValue;
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
    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    /**
     * Sets authorDto.
     *
     * @param authorDtoValue value of authorDto.
     */
    public void setAuthorDto(final AuthorDto authorDtoValue) {
        authorDto = authorDtoValue;
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
    public List<TagDto> getTagDtos() {
        return tagDtos;
    }

    /**
     * Sets tagDtos.
     *
     * @param tagDtosValue value of tagDtos.
     */
    public void setTagDtos(final List<TagDto> tagDtosValue) {
        tagDtos = tagDtosValue;
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
                newsDto.creationDate) && modificationDate.equals(newsDto.modificationDate) && authorDto.equals(
                newsDto.authorDto) && Objects.equals(tagDtos, newsDto.tagDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, creationDate, modificationDate, authorDto, isAuthorNew,
                            tagDtos, isNewsFresh);
    }
}
