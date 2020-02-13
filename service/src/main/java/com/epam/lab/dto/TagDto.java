package com.epam.lab.dto;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("tagDto")
public class TagDto extends AbstractDto {

    private String name;

    public TagDto() {
        super();
    }

    public TagDto(String name) {
        super();
        this.name = name;
    }

    public TagDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
