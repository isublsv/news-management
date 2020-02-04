package com.epam.lab.service;

import com.epam.lab.dto.AbstractDto;
import com.epam.lab.exception.ServiceException;

public interface Service<D extends AbstractDto> {

    D create(D entityDto) throws ServiceException;
    D find(Long id);
    D update(D entityDto);
    void delete(Long id);
}
