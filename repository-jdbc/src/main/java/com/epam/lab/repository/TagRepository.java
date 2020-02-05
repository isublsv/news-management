package com.epam.lab.repository;

import com.epam.lab.model.Tag;

import java.util.List;

public interface TagRepository extends Repository<Tag> {

    Tag findByTag(Tag tag);

    Boolean findByTagName(String name);

    List<Tag> findTagsByNewsId(Long newsId);
}
