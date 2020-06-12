package com.epam.lab.service;

import com.epam.lab.dto.AbstractDto;

import java.util.List;

public interface Service<D extends AbstractDto> {

    D create(D entityDto);

    D find(Long id);

    D update(D entityDto);

    void delete(Long id);
    
    List<D> findAll();
}
