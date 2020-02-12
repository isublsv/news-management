package com.epam.lab.service;

import com.epam.lab.dto.AbstractDto;

public interface Service<D extends AbstractDto> {

    D create(D entityDto);

    D find(Long id);

    D update(D entityDto);

    void delete(Long id);
}
