package com.epam.lab.service;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.TagMapper;
import com.epam.lab.exception.EntityDuplicatedException;
import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.TagRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
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

    @Test(expected = EntityDuplicatedException.class)
    public void shouldThrowExceptionIfTagNameExist() {
        when(tagRepository.create(any(Tag.class))).thenThrow(EntityDuplicatedException.class);

        tagService.create(new TagDto());
        verify(tagRepository).create(any(Tag.class));
    }

    @Test
    public void shouldFindTagById() {
        when(tagRepository.find(anyLong())).thenReturn(tag);

        TagDto actual = tagService.find(anyLong());
        assertEquals(expected, actual);

        verify(tagRepository).find(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenFindTagById() {
        when(tagRepository.find(anyLong())).thenThrow(EntityNotFoundException.class);

        tagService.find(anyLong());
        verify(tagRepository).find(anyLong());
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

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeleteTagByIdIfIdNotExist() {
        doThrow(EntityNotFoundException.class).when(tagRepository).delete(anyLong());

        tagService.delete(anyLong());
    }
}