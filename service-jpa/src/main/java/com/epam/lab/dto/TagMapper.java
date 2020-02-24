package com.epam.lab.dto;

import com.epam.lab.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper extends AbstractMapper<Tag, TagDto> {

    public TagMapper() {
        super(Tag.class, TagDto.class);
    }
}
