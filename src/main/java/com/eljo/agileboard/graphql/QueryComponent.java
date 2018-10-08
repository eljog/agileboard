package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.StoryService;
import com.eljo.agileboard.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Eljo.George on 9/24/2018.
 */
@Component
public class QueryComponent implements GraphQLQueryResolver {

    private InitiativeService initiativeService;
    private UserService userService;
    private StoryService storyService;

    public QueryComponent(InitiativeService initiativeService, UserService userService, StoryService storyService) {
        this.initiativeService = initiativeService;
        this.userService = userService;
        this.storyService = storyService;
    }

    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    public User getUser(long id) {
        return userService.getUser(id);
    }

    public Initiative getInitiative(long id) {
        return initiativeService.getInitiative(id);
    }

    public Iterable<Initiative> getInitiatives() {
        return initiativeService.getInitiatives();
    }

    public Story getStory(long id) {
        return storyService.getStory(id);
    }

    public Iterable<Story> getStories() {
        return storyService.getStories();
    }
}
