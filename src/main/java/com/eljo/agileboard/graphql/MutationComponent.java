package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.InitiativeInput;
import com.eljo.agileboard.graphql.input.StoryInput;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.StoryService;
import com.eljo.agileboard.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@Component
public class MutationComponent implements GraphQLMutationResolver {

    private InitiativeService initiativeService;
    private UserService userService;
    private StoryService storyService;

    public MutationComponent(InitiativeService initiativeService, UserService userService, StoryService storyService) {
        this.initiativeService = initiativeService;
        this.userService = userService;
        this.storyService = storyService;
    }

    public User createUser(UserInput userInput) throws InvalidUserException {
        User user = userInput.convertToUser();
        return userService.createUser(user);
    }

    public Initiative createInitiative(InitiativeInput initiativeInput) throws InvalidRecordExeption {
        Initiative initiative = initiativeInput.convertToInitiative();
        return initiativeService.createInitiative(initiative);
    }

    public Story createStory(StoryInput storyInput) throws InvalidRecordExeption {
        Story story = storyInput.convertToStory();
        return storyService.createStory(story);
    }
}
