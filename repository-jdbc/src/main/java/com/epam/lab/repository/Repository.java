package com.epam.lab.repository;

import com.epam.lab.model.Entity;

public interface Repository<T extends Entity> {

    T create(T entity);
    T find(long id);
    T update(T entity);
    void delete(long id);
}
