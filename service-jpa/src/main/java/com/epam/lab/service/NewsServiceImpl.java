package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.SearchCriteriaBuilder;
import com.epam.lab.dto.TagDto;
import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepositoryValue,
                           final NewsMapper newsMapperValue) {
        this.newsRepository = newsRepositoryValue;
        this.newsMapper = newsMapperValue;
    }

    @Transactional
    @Override
    public NewsDto create(final NewsDto entityDto) {
        News news = newsRepository.create(newsMapper.toEntity(entityDto));
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDto find(final Long id) {
        return newsMapper.toDto(newsRepository.find(id));
    }

    @Transactional
    @Override
    public NewsDto update(final NewsDto entityDto) {
        News news = newsRepository.update(newsMapper.toEntity(entityDto));
        return newsMapper.toDto(news);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        newsRepository.delete(id);
    }

    @Transactional
    @Override
    public List<TagDto> addTagsForNews(final Long newsId, final List<TagDto> tagDtos) {
        return new ArrayList<>();
    }

    @Override
    public Long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public List<NewsDto> searchBy(final SearchCriteria sc) {
        String sql = new SearchCriteriaBuilder()
                .setAuthorName(sc.getName())
                .setAuthorSurname(sc.getSurname())
                .setTags(sc.getTags())
                .setSortAndOrder(sc.getOrderBy(), sc.isDesc())
                .buildSearchQuery();
        return newsRepository.searchBy(sql).stream().map(newsMapper::toDto).collect(Collectors.toList());
    }
}
