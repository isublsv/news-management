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
                //check if news with provided title exists in the DB
                if (!Boolean.TRUE.equals(newsRepository.findNewsByTitle(news.getTitle()))) {
                    createLinkBetweenAuthorAndNews(news, author);
                } else {
                    throw new ServiceException("The news is exists with provided title");
                }
            } else {
                throw new ServiceException("The author entity is invalid");
            }
        } else {
            Author author = authorRepository.create(news.getAuthor());
            createLinkBetweenAuthorAndNews(news, author);
            news.setAuthor(author);
        }
        createNewsTags(news);
        return newsMapper.toDto(news);

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

    private void createLinkBetweenAuthorAndNews(final News news, final Author author) {
        News fullNewsEntity = newsRepository.create(news);
        newsRepository.addNewsAuthor(fullNewsEntity.getId(), author.getId());
        news.setId(fullNewsEntity.getId());
        news.setCreationDate(fullNewsEntity.getCreationDate());
        news.setModificationDate(fullNewsEntity.getModificationDate());
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
                if (tag.getId() != null) {
                    Tag validTag = tagRepository.findByTag(tag);
                    if (validTag != null) {
                        newsRepository.addNewsTag(news.getId(), validTag.getId());
                    }
                } else {
                    Tag newTag = tagRepository.create(tag);
                    if (newTag != null && newsRepository.addNewsTag(news.getId(), newTag.getId())) {
                        tag.setId(newTag.getId());
                    } else {
                        tags.remove(tag);
                    }
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
        String sql = searchCriteria.accept();
        return newsRepository.searchBy(sql).stream().map(newsMapper::toDto).collect(Collectors.toList());
    }
}
