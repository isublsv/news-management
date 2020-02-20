package com.epam.lab.service;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(final TagRepository tagRepositoryValue, final TagMapper tagMapperValue) {
        this.tagRepository = tagRepositoryValue;
        this.tagMapper = tagMapperValue;
    }

    @Transactional
    @Override
    public TagDto create(final TagDto entityDto) {
        Tag tag = tagMapper.toEntity(entityDto);
        return tagMapper.toDto(tagRepository.create(tag));
    }

    @Override
    public TagDto find(final Long id) {
        return tagMapper.toDto(tagRepository.find(id));
    }

    @Transactional
    @Override
    public TagDto update(final TagDto entityDto) {
        Tag tag = tagRepository.update(tagMapper.toEntity(entityDto));
        return tagMapper.toDto(tag);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        tagRepository.delete(id);
    }
}
