package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    public void create(NewsDto entityDto) {
        newsRepository.create(newsMapper.toEntity(entityDto));
        System.out.println("The News was added!");
    }

    @Override
    public NewsDto find(long id) {
        return newsMapper.toDto(newsRepository.find(id));
    }

    @Override
    public void update(NewsDto entityDto) {
        newsRepository.update(newsMapper.toEntity(entityDto));
        System.out.println("The News was updated! " + entityDto);
    }

    @Override
    public void delete(long id) {
        newsRepository.delete(id);
        System.out.println("The News was deleted by id=" + id);
    }

    @Override
    public Long countAllNews() {
        return newsRepository.countAllNews();
    }
}
