package com.epam.lab.repository;

import com.epam.lab.model.Entity;

public interface Repository<T extends Entity> {

    T create(T entity);

    T find(Long id);

    T update(T entity);

    void delete(Long id);
}
