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
    private List<TagDto> tagDtos;

    public NewsDto() {
        super();
    }

    public NewsDto(String title, String shortText, String fullText, LocalDateTime creationDate, LocalDateTime modificationDate, AuthorDto authorDto, List<TagDto> tagDtos) {
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.authorDto = authorDto;
        this.tagDtos = tagDtos;
    }

    public NewsDto(Long id, String title, String shortText, String fullText, LocalDateTime creationDate, LocalDateTime modificationDate, AuthorDto authorDto, List<TagDto> tagDtos) {
        super(id);
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.authorDto = authorDto;
        this.tagDtos = tagDtos;
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

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

    public List<TagDto> getTagDtos() {
        return tagDtos;
    }

    public void setTagDtos(List<TagDto> tagDtos) {
        this.tagDtos = tagDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDto newsDto = (NewsDto) o;
        return Objects.equals(shortText, newsDto.shortText) &&
                Objects.equals(fullText, newsDto.fullText) &&
                Objects.equals(creationDate, newsDto.creationDate) &&
                Objects.equals(modificationDate, newsDto.modificationDate) &&
                Objects.equals(authorDto, newsDto.authorDto) &&
                Objects.equals(tagDtos, newsDto.tagDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortText, fullText, creationDate, modificationDate, authorDto, tagDtos);
    }
}
