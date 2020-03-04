package com.epam.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsDto extends AbstractDto {

    @NotBlank
    @Length(min = 2, max = 30, message = "The news title length must be between 2 and 30 characters.")
    @Pattern(regexp = "^[A-ZА-Я_!?\\-\\d ]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided news title is not valid")
    private String title;

    @NotBlank
    @Length(min = 2, max = 100, message = "The news description length must be between 2 and 100 characters.")
    @Pattern(regexp = "^[A-ZА-Я_!?\\-\\d\\s]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided news description is not valid.")
    private String shortText;

    @NotBlank
    @Length(min = 2, max = 2000, message = "The news text length must be between 2 and 2000 characters.")
    @Pattern(regexp = "^[A-ZА-Я_!?\\-\\d\\s]+",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Provided news text is not valid.")
    private String fullText;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    @NotNull(message = "Author entity cannot be null.")
    @Valid
    private AuthorDto author;

    private List<TagDto> tags = new ArrayList<>();

    public NewsDto() {
        super();
        author = new AuthorDto();
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

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(final AuthorDto authorValue) {
        author = authorValue;
    }

    public List<TagDto> getTags() {
        return tags;
    }

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
