package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.AuthorMapper;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(final AuthorRepository authorRepositoryValue,
                             final NewsRepository newsRepositoryValue,
                             final AuthorMapper authorMapperValue) {
        this.authorRepository = authorRepositoryValue;
        this.newsRepository = newsRepositoryValue;
        this.authorMapper = authorMapperValue;
    }

    @Override
    public AuthorDto create(final AuthorDto entityDto) {
        return authorMapper.toDto(authorRepository.create(authorMapper.toEntity(entityDto)));
    }

    @Override
    public AuthorDto find(final Long id) {
        return authorMapper.toDto(authorRepository.find(id));
    }

    @Override
    public AuthorDto update(final AuthorDto entityDto) {
        Author author = authorRepository.update(authorMapper.toEntity(entityDto));
        return authorMapper.toDto(author);
    }

    @Override
    public void delete(final Long id) {
        List<Long> news = newsRepository.findNewsByAuthorId(id);
        authorRepository.delete(id);
        news.forEach(newsRepository::delete);
    }
}
