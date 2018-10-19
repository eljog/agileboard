package com.eljo.agileboard.graphql;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.InitiativeInput;
import com.eljo.agileboard.graphql.input.ProjectInput;
import com.eljo.agileboard.graphql.input.StoryInput;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.ProjectService;
import com.eljo.agileboard.service.StoryService;
import com.eljo.agileboard.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MutationComponentTest {

    @Mock
    private UserService userService;

    @Mock
    private InitiativeService initiativeService;

    @Mock
    private StoryService storyService;

    @Mock
    private ProjectService projectService;

    @Autowired
    @InjectMocks
    private MutationComponent mutationComponent;

    private UserInput userInput;
    private InitiativeInput initiativeInput;
    private StoryInput storyInput;
    private ProjectInput projectInput;
    private User user;
    private Project project;

    @Test
    public void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userInput.convertToUser());

        User user = mutationComponent.createUser(userInput);

        verify(userService, times(1)).createUser(any());
        assertNotNull(user);
    }

    @Test
    public void createUser_Exception() throws Exception {
        when(userService.createUser(any())).thenThrow(InvalidUserException.class);

        try {
            User user = mutationComponent.createUser(userInput);
            assertFalse(true);
        } catch (InvalidUserException e) {
            assertTrue(true);
        }

        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void createInitiative() throws Exception {
        when(initiativeService.createInitiative(any())).thenReturn(initiativeInput.convertToInitiative());

        Initiative initiative = mutationComponent.createInitiative(initiativeInput);

        verify(initiativeService, times(1)).createInitiative(any());
        assertNotNull(initiative);
    }

    @Test
    public void createInitiative_Exception() throws Exception {
        when(initiativeService.createInitiative(any())).thenThrow(InvalidRecordExeption.class);

        try {
            Initiative initiative = mutationComponent.createInitiative(initiativeInput);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        }

        verify(initiativeService, times(1)).createInitiative(any());
    }

    @Test
    public void createStory() throws Exception {
        when(storyService.createOrUpdateStory(any())).thenReturn(storyInput.convertToStory());

        Story story = mutationComponent.createOrUpdateStory(storyInput);

        verify(storyService, times(1)).createOrUpdateStory(any());
        assertNotNull(story);
    }

    @Test
    public void createStory_Exception() throws Exception {
        when(storyService.createOrUpdateStory(any())).thenThrow(InvalidRecordExeption.class);

        Story story = null;

        try {
            story = mutationComponent.createOrUpdateStory(storyInput);
            assertTrue(false);
        } catch (InvalidRecordExeption invalidRecordExeption) {
            assertTrue(true);
        }

        verify(storyService, times(1)).createOrUpdateStory(any());
        assertNull(story);
    }

    @Test
    public void createProject() throws Exception {
        when(projectService.createOrUpdateProject(any())).thenReturn(projectInput.convertToProject());

        Project project = mutationComponent.createOrUpdateProject(projectInput);

        verify(projectService, times(1)).createOrUpdateProject(any());
        assertNotNull(project);
    }

    @Test
    public void createProject_Exception() throws Exception {
        when(projectService.createOrUpdateProject(any())).thenThrow(InvalidRecordExeption.class);

        Project project = null;

        try {
            project = mutationComponent.createOrUpdateProject(projectInput);
            assertTrue(false);
        } catch (InvalidRecordExeption invalidRecordExeption) {
            assertTrue(true);
        }

        verify(projectService, times(1)).createOrUpdateProject(any());
        assertNull(project);
    }

    @Test
    public void addProjectToUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(project);
        when(userService.addProjectToUser(any(), any())).thenReturn(user);

        User user = mutationComponent.addProjectToUser(1L, 2L);

        verify(userService, times(1)).addProjectToUser(any(), any());
        assertNotNull(user);
    }

    @Test
    public void addProjectToUser_invalidUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(null);

        User updatedUser = null;
        try {
            updatedUser = mutationComponent.addProjectToUser(1L, 1L);
            failBecauseExceptionWasNotThrown(InvalidUserException.class);
        } catch (InvalidUserException e) {
            // Pass
        }

        verify(userService, times(1)).getUser(anyLong());
        verify(userService, never()).addProjectToUser(any(), any());
        verify(projectService, never()).getProject(anyLong());
        assertNull(updatedUser);
    }

    @Test
    public void addProjectToUser_invalidProject() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);
        when(projectService.getProject(anyLong())).thenReturn(null);

        User updatedUser = null;
        try {
            updatedUser = mutationComponent.addProjectToUser(1L, 1L);
            failBecauseExceptionWasNotThrown(InvalidRecordExeption.class);
        } catch (InvalidRecordExeption e) {
            // Pass
        }

        verify(userService, times(1)).getUser(anyLong());
        verify(userService, never()).addProjectToUser(any(), any());
        verify(projectService, times(1)).getProject(anyLong());
        assertNull(updatedUser);

    }


    @Before
    public void setUp() {
        userInput = new UserInput();
        userInput.setEmail("email");
        userInput.setName("name");
        userInput.setUsername("username");
        userInput.setPassword("password");

        initiativeInput = new InitiativeInput();
        initiativeInput.setDetails("details");
        initiativeInput.setName("name");
        initiativeInput.setOwnerId(1L);
        initiativeInput.setStatus("status");

        storyInput = new StoryInput();
        storyInput.setDetails("details");
        storyInput.setName("name");
        storyInput.setOwnerId(1L);
        storyInput.setStatus("status");

        projectInput = new ProjectInput();
        projectInput.setDescription("description");
        projectInput.setName("name");

        this.user = new User("username", "password", "email", "name");
        this.project = new Project("name", "description", user, new Date());
    }

}