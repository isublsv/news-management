package com.epam.lab.service.impl;

import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tagService")
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void create(Tag entity) {
        tagRepository.create(entity);
        System.out.println("The Tag was added!");
    }

    @Override
    public Tag find(long id) {
        return tagRepository.find(id);
    }

    @Override
    public void update(Tag entity) {
        tagRepository.update(entity);
        System.out.println("The Tag was updated! " + entity);
    }

    @Override
    public void delete(long id) {
        tagRepository.delete(id);
        System.out.println("The Tag was deleted by id=" + id);
    }
}
