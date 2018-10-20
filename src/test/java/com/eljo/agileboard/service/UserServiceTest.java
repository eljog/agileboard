package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserService userService;

    private User user;
    private Project project;

    @Test
    public void getUser() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User returnedUser = userService.getUser(1);

        verify(userRepository, times(1)).findById(anyLong());
        assertThat("name").isEqualTo(returnedUser.getName());
    }

    @Test
    public void getUser_notFound() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        User returnedUser = userService.getUser(1);

        verify(userRepository, times(1)).findById(anyLong());
        assertNull(returnedUser);
    }

    @Test
    public void getUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        Iterable<User> userIterable = userService.getUsers();

        verify(userRepository, times(1)).findAll();
        assertThat("username").isEqualTo(userIterable.iterator().next().getUsername());
    }

    @Test
    public void createUser() throws Exception {
        when(userRepository.save(Mockito.any())).thenReturn(user);

        User savedUser = userService.createUser(user);

        verify(userRepository, times(1)).save(any());
        assertThat("name").isEqualTo(savedUser.getName());
    }

    @Test
    public void createUser_Exception() throws Exception {
        // Setting a mandatory attrobute to null
        user.setEmail(null);

        User savedUser = null;
        try {
            savedUser = userService.createUser(user);
            assertTrue(false);
        } catch (InvalidUserException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(userRepository, never()).save(any());
        assertNull(savedUser);
    }

    @Test
    public void fetchUsersByProject() throws Exception {
        when(userRepository.findByProject(any())).thenReturn(Collections.singletonList(user));

        Iterable<User> users = userService.fetchUsersByProject(project);

        verify(userRepository, times(1)).findByProject(any());
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void addProjectToUser() throws Exception {
        when(userRepository.save(any())).thenReturn(user);

        User updatedUser = userService.addProjectToUser(project, user);

        verify(userRepository, times(1)).save(any());
        assertNotNull(updatedUser.getProject());
        assertThat(updatedUser.getProject().getName()).isEqualTo(project.getName());

    }


    @Before
    public void setUp() {
        this.user = new User("username", "password", "email", "name");
        this.user.setId(1L);
        project = new Project(1L, "name", "description", user, new Date());
    }

}