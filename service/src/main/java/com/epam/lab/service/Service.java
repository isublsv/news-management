package com.epam.lab.service;

import com.epam.lab.model.Entity;

public interface Service<T extends Entity> {

    void create(T entity);
    T find(long id);
    void update(T entity);
    void delete(long id);
}
