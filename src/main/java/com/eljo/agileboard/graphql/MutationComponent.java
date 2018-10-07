package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.InitiativeInput;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Component
public class MutationComponent implements GraphQLMutationResolver {

    private InitiativeService initiativeService;
    private UserService userService;

    public MutationComponent(InitiativeService initiativeService, UserService userService) {
        this.initiativeService = initiativeService;
        this.userService = userService;
    }

    public User createUser(UserInput userInput) throws InvalidUserException {
        User user = userInput.convertToUser();
        return userService.createUser(user);
    }

    public Initiative createInitiative(InitiativeInput initiativeInput) throws InvalidRecordExeption {
        Initiative initiative = initiativeInput.convertToInitiative();
        return initiativeService.createInitiative(initiative);
    }
}
