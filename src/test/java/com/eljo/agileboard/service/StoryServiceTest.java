package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.StoryRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private StoryService storyService;

    private Story story;
    private User user;

    @Before
    public void setUp() {
        this.user = new User("username", "password", "email", "name");
        this.story = new Story("name", "details", user, "status");
        this.story.setId(1);
    }

    @Test
    public void getStory() throws Exception {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(story));

        Story returnedStory = storyService.getStory(1);

        verify(storyRepository, times(1)).findById(anyLong());
        assertNotNull(returnedStory);
        assertThat(returnedStory.getId()).isGreaterThan(0L);
    }

    @Test
    public void getStory_notFound() throws Exception {
        when(storyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Story returnedStory = storyService.getStory(1);

        verify(storyRepository, times(1)).findById(anyLong());
        assertNull(returnedStory);
    }

    @Test
    public void getStories() throws Exception {
        when(storyRepository.findAll()).thenReturn(Collections.singletonList(story));

        Iterable<Story> storyIterable = storyService.getStories();

        verify(storyRepository, times(1)).findAll();
        assertThat(storyIterable.iterator().next()).isInstanceOf(Story.class);
    }

    @Test
    public void createStory() throws Exception {
        when(storyRepository.save(any())).thenReturn(story);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        Story savedStory = storyService.createStory(story);

        verify(storyRepository, times(1)).save(any());
        verify(userRepository, times(1)).findById(anyLong());
        assertNotNull(savedStory);
        assertThat(savedStory.getId()).isGreaterThan(0L);
    }

    @Test
    public void createStory_withInvalidOwner() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Story savedStory = null;
        try {
            savedStory = storyService.createStory(story);
            assertTrue(false);
        } catch(InvalidRecordExeption e) {
            if(e.getCause() instanceof InvalidUserException) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        assertNull(savedStory);
    }

    @Test
    public void createStory_withoutMandatoryFields() throws Exception {
        story.setName(null);

        Story savedStory = null;
        try {
            savedStory = storyService.createStory(story);
            assertTrue(false);
        } catch(InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(storyRepository, never()).save(any());
        assertNull(savedStory);
    }

}