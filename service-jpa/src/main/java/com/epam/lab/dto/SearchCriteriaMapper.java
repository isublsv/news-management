package com.epam.lab.dto;

import com.epam.lab.model.SearchCriteria;
import org.springframework.stereotype.Component;

@Component
public class SearchCriteriaMapper extends AbstractMapper<SearchCriteria, SearchCriteriaDto> {

    public SearchCriteriaMapper() {
        super(SearchCriteria.class, SearchCriteriaDto.class);
    }
}
