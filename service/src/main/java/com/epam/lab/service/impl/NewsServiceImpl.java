package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepository,
                           AuthorRepository authorRepository,
                           TagRepository tagRepository,
                           NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    public NewsDto create(NewsDto entityDto) throws Exception {
        News news = newsMapper.toEntity(entityDto);
        long id = entityDto.getAuthorDto().getId();
        if (id != 0) {
            Author author = authorRepository.findByAuthor(news.getAuthor());
            if (author == null) {
                throw new Exception();
            } else {
                News news1 = newsRepository.create(newsMapper.toEntity(entityDto));
                newsRepository.addNewsAuthor(news1.getId(), author.getId());
            }
        }
        List<Tag> tagList = news.getTags();
        if (!tagList.isEmpty()) {
            for (Tag tag : tagList) {
                tagRepository.findByTag(tag);
            }
        }
        return newsMapper.toDto(news);

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
