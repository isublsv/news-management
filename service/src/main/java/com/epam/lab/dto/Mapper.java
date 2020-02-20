package com.epam.lab.dto;

import com.epam.lab.model.Entity;

public interface Mapper<E extends Entity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
