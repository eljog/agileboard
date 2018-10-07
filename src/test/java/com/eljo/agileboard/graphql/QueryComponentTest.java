package com.eljo.agileboard.graphql;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryComponentTest {

    @Mock
    private InitiativeService initiativeService;
    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private QueryComponent queryComponent;

    private User user;
    private Initiative initiative;

    @Before
    public void setUp() {
        this.user = new User("username", "password", "email", "name");
        this.user.setId(1);
        this.initiative = new Initiative("name", "details", user, "status");
    }

    @Test
    public void getUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(user));

        Iterable<User> userIterable = queryComponent.getUsers();

        verify(userService, times(1)).getUsers();
        assertThat("name").isEqualTo(userIterable.iterator().next().getName());
    }

    @Test
    public void getUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);

        User userReturned = queryComponent.getUser(1);

        verify(userService, times(1)).getUser(anyLong());
        assertThat("username").isEqualTo(userReturned.getUsername());
    }

    @Test
    public void getInitiative() throws Exception {
        when(initiativeService.getInitiative(anyLong())).thenReturn(initiative);

        Initiative initiativeReturned = queryComponent.getInitiative(1);

        verify(initiativeService, times(1)).getInitiative(anyLong());
        assertThat("name").isEqualTo(initiativeReturned.getName());
    }

    @Test
    public void getInitiatives() throws Exception {
        when(initiativeService.getInitiatives()).thenReturn(Collections.singletonList(initiative));

        Iterable<Initiative> initiativeIterable = queryComponent.getInitiatives();

        verify(initiativeService, times(1)).getInitiatives();
        assertThat("details").isEqualTo(initiativeIterable.iterator().next().getDetails());
    }

}