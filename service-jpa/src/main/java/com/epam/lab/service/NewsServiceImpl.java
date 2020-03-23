package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.dto.SearchCriteriaMapper;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.model.News;
import com.epam.lab.model.Page;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final TagMapper tagMapper;
    private final SearchCriteriaMapper searchCriteriaMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepositoryValue,
                           final NewsMapper newsMapperValue,
                           final TagMapper tagMapperValue,
                           final SearchCriteriaMapper searchCriteriaMapperValue) {
        this.newsRepository = newsRepositoryValue;
        this.newsMapper = newsMapperValue;
        this.tagMapper = tagMapperValue;
        this.searchCriteriaMapper = searchCriteriaMapperValue;
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

    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll().stream().map(newsMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<TagDto> addTagsForNews(final Long newsId, final List<TagDto> tagDtos) {
        List<Tag> tags = tagDtos.stream().map(tagMapper::toEntity).collect(Collectors.toList());
        List<Tag> newsTags = newsRepository.addTagsForNews(newsId, tags);
        return newsTags.stream().map(tagMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public Page<NewsDto> searchBy(final SearchCriteriaDto sc) {
        SearchCriteria searchCriteria = searchCriteriaMapper.toEntity(sc);
        Page<News> newsPage = newsRepository.searchBy(searchCriteria);        
        return new Page<>(newsPage.getEntities().stream().map(newsMapper::toDto).collect(Collectors.toList()),
                          newsPage.getTotalCount());
    }
}
