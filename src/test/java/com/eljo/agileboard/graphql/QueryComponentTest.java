package com.eljo.agileboard.graphql;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.StoryService;
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
import static org.junit.Assert.assertNotNull;
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

    @Mock
    private StoryService storyService;

    @Autowired
    @InjectMocks
    private QueryComponent queryComponent;

    private User user;
    private Initiative initiative;
    private Story story;

    @Test
    public void getUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(user));

        Iterable<User> userIterable = queryComponent.getUsers();

        verify(userService, times(1)).getUsers();
        assertThat(userIterable.iterator().next()).isInstanceOf(User.class);
    }

    @Test
    public void getUser() throws Exception {
        when(userService.getUser(anyLong())).thenReturn(user);

        User userReturned = queryComponent.getUser(1);

        verify(userService, times(1)).getUser(anyLong());
        assertNotNull(userReturned);
    }

    @Test
    public void getInitiative() throws Exception {
        when(initiativeService.getInitiative(anyLong())).thenReturn(initiative);

        Initiative initiativeReturned = queryComponent.getInitiative(1);

        verify(initiativeService, times(1)).getInitiative(anyLong());
        assertNotNull(initiativeReturned);
    }

    @Test
    public void getInitiatives() throws Exception {
        when(initiativeService.getInitiatives()).thenReturn(Collections.singletonList(initiative));

        Iterable<Initiative> initiativeIterable = queryComponent.getInitiatives();

        verify(initiativeService, times(1)).getInitiatives();
        assertThat(initiativeIterable.iterator().next()).isInstanceOf(Initiative.class);
    }

    @Test
    public void getStory() throws Exception {
        when(storyService.getStory(anyLong())).thenReturn(story);

        Story returnedStory = queryComponent.getStory(1);

        verify(storyService, times(1)).getStory(anyLong());
        assertThat(returnedStory).isInstanceOf(Story.class);
    }

    @Test
    public void getStories() throws Exception {
        when(storyService.getStories()).thenReturn(Collections.singletonList(story));

        Iterable<Story> storyIterable = queryComponent.getStories();

        verify(storyService, times(1)).getStories();
        assertThat(storyIterable.iterator().next()).isInstanceOf(Story.class);
    }

    @Before
    public void setUp() {
        this.user = new User("username", "password", "email", "name");
        this.user.setId(1);
        this.initiative = new Initiative("name", "details", user, "status");
        this.story = new Story("name", "details", user, "status");
    }

}