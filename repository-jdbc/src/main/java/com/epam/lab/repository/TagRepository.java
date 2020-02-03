package com.epam.lab.repository;

import com.epam.lab.model.Tag;

public interface TagRepository extends Repository<Tag> {

    Tag findByTag(Tag tag);
}
