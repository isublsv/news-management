package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.SearchCriteriaBuilder;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.RepositoryException;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NewsMapper newsMapper;
    private final TagMapper tagMapper;

    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepositoryValue,
            final AuthorRepository authorRepositoryValue,
            final TagRepository tagRepositoryValue,
            final NewsMapper newsMapperValue,
            final TagMapper tagMapperValue) {
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
                //check if news with provided title exists in the DB
                checkNewsActuality(news, author);
            } else {
                throw new ServiceException("The author entity is invalid");
            }
        } else {
            //create a new author and make link to current news entity
            Author author = authorRepository.create(news.getAuthor());
            checkNewsActuality(news, author);
        }
        NewsDto newsDto = newsMapper.toDto(news);
        newsDto.setTags(addTagsForNews(news.getId(), entityDto.getTags()));
        return newsDto;
    }

    private void checkNewsActuality(final News news, final Author author) {
        if (!Boolean.TRUE.equals(newsRepository.findNewsByTitle(news.getTitle()))) {
            createLinkBetweenAuthorAndNews(news, author);
        } else {
            throw new ServiceException("The news is exists with provided title");
        }
    }

    private void createLinkBetweenAuthorAndNews(final News news, final Author author) {
        News fullNewsEntity = newsRepository.create(news);
        newsRepository.addNewsAuthor(fullNewsEntity.getId(), author.getId());

        news.setId(fullNewsEntity.getId());
        news.setCreationDate(fullNewsEntity.getCreationDate());
        news.setModificationDate(fullNewsEntity.getModificationDate());
        news.setAuthor(author);
    }

    @Override
    public NewsDto find(final Long id) {
        News news = newsRepository.find(id);
        Author author = authorRepository.find(news.getAuthor().getId());
        news.setAuthor(author);
        List<Tag> tags = tagRepository.findTagsByNewsId(news.getId());
        news.setTags(tags);
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDto update(final NewsDto entityDto) {
        News news = newsRepository.update(newsMapper.toEntity(entityDto));
        Author author = news.getAuthor();
        //if author exist
        if (author != null) {
            Long authorId = author.getId();
            //if author has ID
            if (authorId != null) {
                //than find author in the DB
                Author validAuthor = authorRepository.findByAuthor(author);
                //if author is valid
                if (validAuthor != null) {
                    //than add this author for provided news
                    newsRepository.addNewsAuthor(news.getId(), authorId);
                    news.setAuthor(validAuthor);
                } else {
                    throw new ServiceException("The author entity is invalid");
                }
                //if author hasn't ID
            } else {
                //create a new author entity in the DB
                Author newAuthor = authorRepository.create(author);
                news.setAuthor(newAuthor);
            }
        }
        NewsDto newsDto = newsMapper.toDto(news);
        newsDto.setTags(addTagsForNews(news.getId(), entityDto.getTags()));
        return newsDto;
    }

    @Override
    public void delete(final Long id) {
        try {
            newsRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagDto> addTagsForNews(final Long newsId, final List<TagDto> tagDtoList) {
        Set<Tag> tags = new HashSet<>(tagRepository.findTagsByNewsId(newsId));
        tags.addAll(tagDtoList.stream().map(tagMapper::toEntity).collect(Collectors.toList()));
        tagRepository.removeTagsByNewsId(newsId);

        Iterator<Tag> it = tags.iterator();
        while (it.hasNext()) {
            Tag tag = it.next();
            //if tag has ID
            if (tag.getId() != null) {
                //than check if tag is valid
                Tag validTag = tagRepository.findByTag(tag);
                //if its valid than add this tag for provided news
                if (validTag != null) {
                    //than add this tag for provided news
                    newsRepository.addNewsTag(newsId, tag.getId());
                } else {
                    //else remove from the list
                    it.remove();
                }
            } else {
                //if a tag hasn't ID than we'll try to add it to the DB
                Tag newTagId = tagRepository.create(tag);
                //if the operation above was complete successfully
                if (newTagId != null) {
                    //than add this tag for provided news
                    newsRepository.addNewsTag(newsId, newTagId.getId());
                    tag.setId(newTagId.getId());
                    //else there is a tag with the same name in the DB
                } else {
                    //find tag ID by name
                    Tag tagByName = tagRepository.findByTagName(tag.getName());
                    //than add this tag for provided news
                    if (tagByName != null) {
                        newsRepository.addNewsTag(newsId, tagByName.getId());
                        tag.setId(tagByName.getId());
                    }
                }
            }
        }
        return tags.stream().map(tagMapper::toDto).collect(Collectors.toList());
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
