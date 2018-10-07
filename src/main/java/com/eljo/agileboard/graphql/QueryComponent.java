package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by Eljo.George on 9/24/2018.
 */
@Component
public class QueryComponent implements GraphQLQueryResolver {

    private InitiativeService initiativeService;
    private UserService userService;

    public QueryComponent(InitiativeService initiativeService, UserService userService) {
        this.initiativeService = initiativeService;
        this.userService = userService;
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
}
