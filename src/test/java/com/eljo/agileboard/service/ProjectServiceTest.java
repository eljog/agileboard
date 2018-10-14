package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.ProjectRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Eljo.George on 10/13/2018.
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    @Autowired
    private ProjectService projectService;

    private Project newProject;
    private Project existingProject;
    private User member;

    @Before
    public void setUp() throws Exception {
        member = new User("username", "password", "email", "name");
        member.setId(1L);
        newProject = new Project("name", "description", member, new Date());
        existingProject = new Project(1L, "name", "description", member, new Date());
    }

    @Test
    public void getProject() throws Exception {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(existingProject));

        Project project = projectService.getProject(1L);

        verify(projectRepository, times(1)).findById(anyLong());
        assertNotNull(project);
        assertThat(project).isInstanceOf(Project.class);
    }

    @Test
    public void getProject_invalidId() throws Exception {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Project project = projectService.getProject(1L);

        verify(projectRepository, times(1)).findById(anyLong());
        assertNull(project);
    }

    @Test
    public void getProjects() throws Exception {
        when(projectRepository.findAll()).thenReturn(Collections.singletonList(existingProject));

        Iterable<Project> projectIterable = projectService.getProjects();

        verify(projectRepository, times(1)).findAll();
        assertThat(projectIterable.iterator().next()).isInstanceOf(Project.class);
    }

    @Test
    public void createOrUpdateProject() throws Exception {
    }

    @Test
    public void createProject() throws Exception {
        when(projectRepository.save(any())).thenReturn(existingProject);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(member));
        when(userService.addProjectToUser(any(), any())).thenReturn(member);

        Project savedProject = projectService.createOrUpdateProject(newProject);

        verify(projectRepository, times(1)).save(any());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userService, times(1)).addProjectToUser(any(), any());
        assertNotNull(savedProject);
        assertThat(savedProject.getId()).isGreaterThan(0L);
    }

    @Test
    public void createProject_withInvalidOwner() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Project savedProject = null;
        try {
            savedProject = projectService.createOrUpdateProject(newProject);
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

        verify(projectRepository, never()).save(any());
        assertNull(savedProject);
    }

    @Test
    public void createProject_withoutMandatoryFields() throws Exception {
        newProject.setName(null);

        Project savedProject = null;
        try {
            savedProject = projectService.createOrUpdateProject(newProject);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(projectRepository, never()).save(any());
        assertNull(savedProject);
    }

    @Test
    public void updateProject() throws Exception {
        when(projectRepository.save(any())).thenReturn(existingProject);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(existingProject));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(member));

        Project savedProject = projectService.createOrUpdateProject(existingProject);

        verify(projectRepository, times(1)).save(any());
        verify(userRepository, times(1)).findById(anyLong());
        assertNotNull(savedProject);
        assertThat(savedProject.getId()).isGreaterThan(0L);
    }

    @Test
    public void updateProject_nonExisting() throws Exception {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(member));

        Project savedProject = null;
        try {
            savedProject = projectService.createOrUpdateProject(existingProject);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        }

        verify(projectRepository, never()).save(any());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        assertNull(savedProject);
    }

    @Test
    public void updateProject_withInvalidCreator() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Project savedProject = null;
        try {
            savedProject = projectService.createOrUpdateProject(existingProject);
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

        verify(projectRepository, never()).save(any());
        assertNull(savedProject);
    }

    @Test
    public void updateProject_withoutMandatoryFields() throws Exception {
        existingProject.setName(null);

        Project savedProject = null;
        try {
            savedProject = projectService.createOrUpdateProject(existingProject);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(projectRepository, never()).save(any());
        assertNull(savedProject);
    }

    @Test
    public void createOrUpdateProject_nulls() throws Exception {
        Project project = null;
        try {
            project = projectService.createOrUpdateProject(null);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(projectRepository, never()).save(any());
        assertNull(project);
    }

}