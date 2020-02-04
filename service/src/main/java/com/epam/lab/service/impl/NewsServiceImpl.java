package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.ServiceException;
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
import java.util.stream.Collectors;

@Transactional
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsMapper newsMapper;
    private final TagMapper tagMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepositoryValue, final AuthorRepository authorRepositoryValue,
            final TagRepository tagRepositoryValue, final NewsMapper newsMapperValue, final TagMapper tagMapperValue) {
        this.newsRepository = newsRepositoryValue;
        this.authorRepository = authorRepositoryValue;
        this.tagRepository = tagRepositoryValue;
        this.newsMapper = newsMapperValue;
        this.tagMapper = tagMapperValue;
    }

    @Override
    public NewsDto create(final NewsDto entityDto) {
        News news = newsMapper.toEntity(entityDto);
        Long authorId = entityDto.getAuthor().getId();
        //check if author has ID
        if (authorId != null) {
            //check if provided author's name, surname and ID matches the values from DB
            Author author = authorRepository.findByAuthor(news.getAuthor());
            if (author != null) {
                if (entityDto.isNewsFresh()) {
                    long newsId = newsRepository.create(news).getId();
                    newsRepository.addNewsAuthor(newsId, author.getId());
                    news.setId(newsId);
                } else {
                    throw new ServiceException("The news is exists with provided title");
                }
            } else {
                throw new ServiceException("The author entity is invalid");
            }
        } else {
            Author author;
            //check if author is new than create new value in DB else find author by name and surname in DB
            if (entityDto.isAuthorNew()) {
                author = authorRepository.create(news.getAuthor());
            } else {
                author = authorRepository.findAuthorByNameAndSurname(news.getAuthor());
            }
            long newsId = newsRepository.create(news).getId();
            newsRepository.addNewsAuthor(newsId, author.getId());
            news.setAuthor(author);
            news.setId(newsId);
        }
        createNewsTags(news);
        return newsMapper.toDto(news);

    }

    @Override
    public NewsDto find(final Long id) {
        return newsMapper.toDto(newsRepository.find(id));
    }

    @Override
    public NewsDto update(final NewsDto entityDto) {
        News news = newsRepository.update(newsMapper.toEntity(entityDto));
        return newsMapper.toDto(news);
    }

    @Override
    public void delete(final Long id) {
        newsRepository.delete(id);
    }

    private void createNewsTags(final News news) {
        List<Tag> tags = news.getTags();
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                Tag tagWithId = tagRepository.findByTag(tag);
                if (tagWithId != null) {
                    newsRepository.addNewsTag(news.getId(), tagWithId.getId());
                    tag.setId(tagWithId.getId());
                } else {

                    long tagId = tagRepository.create(tag).getId();
                    newsRepository.addNewsTag(news.getId(), tagId);
                    tag.setId(tagId);
                }
            }
        }
    }

    @Override
    public void addTagsForNews(final NewsDto newsDto, final List<TagDto> tagDtos) {
        News news = newsMapper.toEntity(newsDto);
        for (TagDto tagDto : tagDtos) {
            Tag tag = tagRepository.findByTag((tagMapper.toEntity(tagDto)));
            if (tag != null) {
                if (!news.getTags().contains(tag)) {
                    newsRepository.addNewsTag(news.getId(), tag.getId());
                }
            } else {
                long tagId = tagRepository.create(tagMapper.toEntity(tagDto)).getId();
                newsRepository.addNewsTag(news.getId(), tagId);
            }
        }
    }

    @Override
    public Long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public List<NewsDto> searchBy(final SearchCriteria searchCriteria) {
        String sql = "" + searchCriteria.accept();
        return newsRepository.searchBy(sql).stream().map(newsMapper::toDto).collect(Collectors.toList());
    }
}
