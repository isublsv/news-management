package com.epam.lab.dto.mapper;

import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tagMapper")
public class TagMapper extends AbstractMapper<Tag, TagDto> {

    @Autowired
    public TagMapper() {
        super(Tag.class, TagDto.class);
    }
}
