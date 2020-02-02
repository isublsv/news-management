package com.epam.lab.service;

import com.epam.lab.dto.AbstractDto;
import com.epam.lab.exception.ServiceException;

public interface Service<D extends AbstractDto> {

    D create(D entityDto) throws ServiceException;
    D find(long id);
    void update(D entityDto);
    void delete(long id);
}
