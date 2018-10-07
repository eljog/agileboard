package com.eljo.agileboard.graphql;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.InitiativeInput;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    @Autowired
    @InjectMocks
    private MutationComponent mutationComponent;

    private UserInput userInput;
    private InitiativeInput initiativeInput;

    @Test
    public void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userInput.convertToUser());

        User user = mutationComponent.createUser(userInput);

        verify(userService, times(1)).createUser(any());
        assertThat("name").isEqualTo(user.getName());
    }

    @Test
    public void createUser_Exception() throws Exception {
        when(userService.createUser(any())).thenThrow(InvalidUserException.class);

        try {
            User user = mutationComponent.createUser(userInput);
            assertFalse(true);
        } catch (InvalidUserException e) {
            assertTrue(true);
        } catch (Exception e) {
            assertFalse(true);
        }

        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void createInitiative() throws Exception {
        when(initiativeService.createInitiative(any())).thenReturn(initiativeInput.convertToInitiative());

        Initiative initiative = mutationComponent.createInitiative(initiativeInput);

        verify(initiativeService, times(1)).createInitiative(any());
        assertThat("details").isEqualTo(initiative.getDetails());
    }

    @Test
    public void createInitiative_Exception() throws Exception {
        when(initiativeService.createInitiative(any())).thenThrow(InvalidRecordExeption.class);

        try {
            Initiative initiative = mutationComponent.createInitiative(initiativeInput);
            assertTrue(false);
        } catch (InvalidRecordExeption e) {
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(initiativeService, times(1)).createInitiative(any());
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
        initiativeInput.setOwnerId(1);
        initiativeInput.setStatus("status");
    }

}