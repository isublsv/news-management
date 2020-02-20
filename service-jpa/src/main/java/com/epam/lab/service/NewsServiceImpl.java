package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsMapper;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.SearchCriteriaBuilder;
import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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
        return handleNews(entityDto);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        newsRepository.delete(id);
    }

    private NewsDto handleNews(final NewsDto entityDto) {
        News news = newsMapper.toEntity(entityDto);
        Long authorId = news.getAuthor().getId();
        if (authorId != null) {
            validateAuthorAndHandleNews(news);
        } else {
            Author newAuthor = authorRepository.create(news.getAuthor());
            validateAndHandleNews(news, newAuthor);
        }
        NewsDto newsDto = newsMapper.toDto(news);
        newsDto.setTags(addTagsForNews(news.getId(), entityDto.getTags()));
        return newsDto;
    }

    private void validateAuthorAndHandleNews(final News news) {
        try {
            Author validAuthor = authorRepository.findByAuthor(news.getAuthor());
            validateAndHandleNews(news, validAuthor);
        } catch (EmptyResultDataAccessException e) {
            throw new ServiceException("The author entity is invalid", e);
        }
    }

    private void validateAndHandleNews(final News news, final Author author) {
        if (isNewsNotActual(news)) {
            handleNewsAndLinkToAuthor(news, author);
        } else {
            throw new ServiceException("The news is exists with provided title");
        }
    }

    private boolean isNewsNotActual(final News news) {
        return !Boolean.TRUE.equals(newsRepository.findNewsByTitle(news.getTitle()));
    }

    private void handleNewsAndLinkToAuthor(final News news, final Author author) {
        News handledNews;
        if (news.getId() != null) {
            handledNews = newsRepository.update(news);
            newsRepository.removeNewsAuthor(handledNews.getId());
        } else {
            handledNews = newsRepository.create(news);
        }
        newsRepository.addNewsAuthor(handledNews.getId(), author.getId());

        news.setId(handledNews.getId());
        news.setCreationDate(handledNews.getCreationDate());
        news.setModificationDate(handledNews.getModificationDate());
        news.setAuthor(author);
    }

    @Transactional
    @Override
    public List<TagDto> addTagsForNews(final Long newsId, final List<TagDto> tagDtos) {
        Set<Tag> tags = new HashSet<>(tagRepository.findTagsByNewsId(newsId));
        tags.addAll(tagDtos.stream().map(tagMapper::toEntity).collect(Collectors.toList()));
        tagRepository.removeTagsByNewsId(newsId);

        handleNewsTags(newsId, tags);
        return convertTagsToDtos(tags);
    }

    private void handleNewsTags(final Long newsId, final Set<Tag> tags) {
        Iterator<Tag> it = tags.iterator();
        while (it.hasNext()) {
            Tag tag = it.next();
            if (tag.getId() != null) {
                try {
                    Tag validTag = tagRepository.findByTag(tag);
                    newsRepository.addNewsTag(newsId, validTag.getId());
                } catch (EmptyResultDataAccessException e) {
                    it.remove();
                }
            } else {
                Tag handledTag = handleTag(tag);
                newsRepository.addNewsTag(newsId, handledTag.getId());
                tag.setId(handledTag.getId());
            }
        }
    }

    private Tag handleTag(final Tag tag) {
        Tag handledTag;
        try {
            handledTag = tagRepository.findByTagName(tag.getName());
        } catch (EmptyResultDataAccessException e) {
            handledTag = tagRepository.create(tag);
        }
        return handledTag;
    }

    private List<TagDto> convertTagsToDtos(final Set<Tag> tagsValue) {
        return tagsValue.stream().map(tagMapper::toDto).collect(Collectors.toList());
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
