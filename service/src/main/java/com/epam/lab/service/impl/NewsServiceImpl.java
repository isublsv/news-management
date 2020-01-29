package com.epam.lab.service.impl;

import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public void create(News entity) {
        newsRepository.create(entity);
        System.out.println("The News was added!");
    }

    @Override
    public News find(long id) {
        return newsRepository.find(id);
    }

    @Override
    public void update(News entity) {
        newsRepository.update(entity);
        System.out.println("The News was updated!");
    }

    @Override
    public void delete(long id) {
        newsRepository.delete(id);
        System.out.println("The News was deleted!");
    }
}
