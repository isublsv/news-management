package com.epam.lab.repository;

import com.epam.lab.model.Entity;

public interface Repository<T extends Entity> {

    void create(T entity);
    T find(long id);
    void update(T entity);
    void delete(long id);
}
