package com.epam.lab.repository;

import com.epam.lab.model.AbstractEntity;

public interface Repository<T extends AbstractEntity> {

    T create(T entity);

    T find(Long id);

    T update(T entity);

    void delete(Long id);
}
