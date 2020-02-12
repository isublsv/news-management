package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("tagService")
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(final TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public void create(TagDto entityDto) {
        tagRepository.create(tagMapper.toEntity(entityDto));
        System.out.println("The Tag was added!");
    }

    @Override
    public TagDto find(long id) {
        return tagMapper.toDto(tagRepository.find(id));
    }

    @Override
    public void update(TagDto entityDto) {
        tagRepository.update(tagMapper.toEntity(entityDto));
        System.out.println("The Tag was updated! " + entityDto);
    }

    @Override
    public void delete(long id) {
        tagRepository.delete(id);
        System.out.println("The Tag was deleted by id=" + id);
    }
}
