package com.epam.lab.service;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.TagRepositoryImpl;
import com.epam.lab.service.TagService;
import com.epam.lab.service.TagServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    private TagService tagService;
    private TagRepository tagRepository;

    private Tag tag;
    private TagDto expected;

    @Before
    public void setUp() {
        tagRepository = mock(TagRepositoryImpl.class);
        TagMapper tagMapper = new TagMapper();
        tagService = new TagServiceImpl(tagRepository, tagMapper);

        tag = new Tag(1L, "TestTag");
        expected = new TagDto(1L, "TestTag");
    }

    @Test
    public void shouldCreateTagByDtoValue() {
        when(tagRepository.create(any(Tag.class))).thenReturn(tag);

        TagDto actual = tagService.create(new TagDto());
        assertEquals(expected, actual);

        verify(tagRepository).create(any(Tag.class));
    }

    @Test
    public void shouldFindTagById() {
        when(tagRepository.find(anyLong())).thenReturn(tag);

        TagDto actual = tagService.find(anyLong());
        assertEquals(expected, actual);

        verify(tagRepository).find(anyLong());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindTagById() {
        when(tagService.find(anyLong())).thenThrow(EmptyResultDataAccessException.class);
        tagService.find(anyLong());
    }

    @Test
    public void shouldUpdateTagByDtoValue() {
        when(tagRepository.update(any(Tag.class))).thenReturn(tag);

        TagDto actual = tagService.update(new TagDto());
        assertEquals(expected, actual);

        verify(tagRepository).update(any(Tag.class));
    }

    @Test
    public void shouldDeleteTagById() {
        tagService.delete(anyLong());

        verify(tagRepository).delete(anyLong());
    }
}
