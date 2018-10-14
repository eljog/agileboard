package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.StoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Autowired
    @InjectMocks
    private StoryService storyService;

    private Story newStory;
    private Story existingStory;
    private User user;
    private Project project;

    @Before
    public void setUp() {
        this.user = new User("username", "password", "email", "name");
        user.setId(1L);
        this.newStory = new Story("name", "details", user, "status");
        this.existingStory = new Story("name", "details", user, "status");
        this.existingStory.setId(Long.valueOf(1));
        this.project = new Project(1L, "name", "description", user, new Date());
        newStory.setProject(project);
        existingStory.setProject(project);
    }

    @Test
    public void getStory() throws Exception {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(existingStory));

        Story returnedStory = storyService.getStory(1L);

        verify(storyRepository, times(1)).findById(anyLong());
        assertNotNull(returnedStory);
        assertThat(returnedStory.getId()).isGreaterThan(0L);
    }

    @Test
    public void getStory_notFound() throws Exception {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Story returnedStory = storyService.getStory(1L);

        verify(storyRepository, times(1)).findById(anyLong());
        assertNull(returnedStory);
    }

    @Test
    public void getStories() throws Exception {
        when(storyRepository.findAll()).thenReturn(Collections.singletonList(newStory));

        Iterable<Story> storyIterable = storyService.getStories();

        verify(storyRepository, times(1)).findAll();
        assertThat(storyIterable.iterator().next()).isInstanceOf(Story.class);
    }

    @Test
    public void createStory() throws Exception {
        when(storyRepository.save(any())).thenReturn(existingStory);
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(project);

        Story savedStory = storyService.createOrUpdateStory(newStory);

        verify(storyRepository, times(1)).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, times(1)).getProject(anyLong());
        assertNotNull(savedStory);
        assertThat(savedStory.getId()).isGreaterThan(0L);
    }

    @Test
    public void createStory_withInvalidOwner() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(newStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            if (e.getCause() instanceof InvalidUserException) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, never()).getProject(anyLong());
        assertNull(savedStory);
    }

    @Test
    public void createStory_withInvalidProject() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(newStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {

            assertTrue(true);
        }

        verify(storyRepository, never()).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, times(1)).getProject(anyLong());
        assertNull(savedStory);
    }

    @Test
    public void createStory_withoutMandatoryFields() throws Exception {
        newStory.setName(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(newStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        assertNull(savedStory);
    }

    @Test
    public void updateStory() throws Exception {
        when(storyRepository.save(any())).thenReturn(existingStory);
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(existingStory));
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(project);

        Story savedStory = storyService.createOrUpdateStory(existingStory);

        verify(storyRepository, times(1)).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, times(1)).getProject(anyLong());
        assertNotNull(savedStory);
        assertThat(savedStory.getId()).isGreaterThan(0L);
    }

    @Test
    public void updateStory_nonExisting() throws Exception {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(project);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(existingStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        }

        verify(storyRepository, never()).save(any());
        verify(storyRepository, times(1)).findById(anyLong());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, times(1)).getProject(anyLong());
        assertNull(savedStory);
    }

    @Test
    public void updateStory_withInvalidOwner() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(existingStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            if (e.getCause() instanceof InvalidUserException) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }

        verify(storyRepository, never()).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, never()).getProject(anyLong());
        assertNull(savedStory);
    }

    @Test
    public void updateStory_withInvalidProject() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(existingStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        }

        verify(storyRepository, never()).save(any());
        verify(userService, times(1)).getUser(anyLong());
        verify(projectService, times(1)).getProject(anyLong());
        assertNull(savedStory);
    }

    @Test
    public void updateStory_withoutMandatoryFields() throws Exception {
        existingStory.setName(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(existingStory);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        assertNull(savedStory);
    }

    @Test
    public void createOrUpdateStory_nulls() throws Exception {
        Story savedStory = null;
        try {
            savedStory = storyService.createOrUpdateStory(null);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        assertNull(savedStory);
    }

}