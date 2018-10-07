package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.eljo.agileboard.domain.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by Eljo.George on 9/24/2018.
 */
@Component
public class QueryComponent implements GraphQLQueryResolver {

    public List<User> getUsers() {
        return (List<User>) Collections.EMPTY_LIST;
    }

    public User getUser(long id) {
        return null;
    }
}
