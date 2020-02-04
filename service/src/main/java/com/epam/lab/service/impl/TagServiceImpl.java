package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Tag;
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
    public TagServiceImpl(final TagRepository tagRepositoryValue, final TagMapper tagMapperValue) {
        this.tagRepository = tagRepositoryValue;
        this.tagMapper = tagMapperValue;
    }

    @Override
    public TagDto create(final TagDto entityDto) {
        return tagMapper.toDto(tagRepository.create(tagMapper.toEntity(entityDto)));
    }

    @Override
    public TagDto find(final Long id) {
        return tagMapper.toDto(tagRepository.find(id));
    }

    @Override
    public TagDto update(final TagDto entityDto) {
        Tag tag = tagRepository.update(tagMapper.toEntity(entityDto));
        return tagMapper.toDto(tag);
    }

    @Override
    public void delete(final Long id) {
        try {
            tagRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
