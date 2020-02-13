package com.epam.lab.dto;

import com.epam.lab.model.Entity;
import org.modelmapper.ModelMapper;

import java.util.Objects;

public abstract class AbstractMapper<E extends Entity, D extends AbstractDto> implements Mapper<E, D> {

    private ModelMapper mapper;

    private Class<E> entity;
    private Class<D> dto;

    public AbstractMapper(final Class<E> entity, final Class<D> dto) {
        this.entity = entity;
        this.dto = dto;
        mapper = new ModelMapper();
    }

    @Override
    public E toEntity(final D dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entity);
    }

    @Override
    public D toDto(final E entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dto);
    }
}
