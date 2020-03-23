package com.epam.lab.model;

import java.util.List;

public class Page<T extends Pageable> {
    
    private List<T> entities;
    private long totalCount;

    public Page() {
    }

    public Page(final List<T> entitiesValue, final long totalCountValue) {
        entities = entitiesValue;
        totalCount = totalCountValue;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(final List<T> entitiesValue) {
        entities = entitiesValue;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final long totalCountValue) {
        totalCount = totalCountValue;
    }
}
