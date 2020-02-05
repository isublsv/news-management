package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDto;
import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.service.TagService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    private static TagService tagService;
    private static TagRepository tagRepository;

    private static Tag tag;
    private static TagDto expected;

    @BeforeClass
    public static void setUp() throws Exception {
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

        verify(tagRepository, times(1)).create(any(Tag.class));
    }

    @Test
    public void shouldFindTagById() {
        long tagId = 1L;
        when(tagRepository.find(tagId)).thenReturn(tag);

        TagDto actual = tagService.find(tagId);
        assertEquals(expected, actual);

        verify(tagRepository, times(1)).find(tagId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowExceptionAfterFindTagById() {
        long tagId = 2L;
        when(tagService.find(tagId)).thenThrow(EmptyResultDataAccessException.class);
        tagService.find(tagId);
    }

    @Test
    public void shouldUpdateTagByDtoValue() {
        when(tagRepository.update(any(Tag.class))).thenReturn(tag);

        TagDto actual = tagService.update(new TagDto());
        assertEquals(expected, actual);

        verify(tagRepository, times(1)).update(any(Tag.class));
    }

    @Test
    public void shouldDeleteTagById() {
        long tagId = 3L;
        tagService.delete(tagId);

        verify(tagRepository, times(1)).delete(tagId);
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionAfterDeleteTagById() {
        long tagId = 4L;

        doThrow(RepositoryException.class).when(tagRepository).delete(tagId);
        tagService.delete(tagId);
    }
}