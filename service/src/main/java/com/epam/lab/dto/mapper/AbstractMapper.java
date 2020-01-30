package com.epam.lab.dto.mapper;

import com.epam.lab.dto.AbstractDto;
import com.epam.lab.model.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractMapper<E extends Entity, D extends AbstractDto> implements Mapper<E, D> {

    @Autowired
    ModelMapper mapper;

    private Class<E> entity;
    private Class<D> dto;

    public AbstractMapper(Class<E> entity, Class<D> dto) {
        this.entity = entity;
        this.dto = dto;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entity);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dto);
    }
}
