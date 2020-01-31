package com.epam.lab.service;

import com.epam.lab.dto.AbstractDto;

public interface Service<D extends AbstractDto> {

    D create(D entityDto) throws Exception;
    D find(long id);
    void update(D entityDto);
    void delete(long id);
}
