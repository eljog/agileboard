package com.eljo.agileboard.configuration;

import com.eljo.agileboard.graphql.MutationComponent;
import com.eljo.agileboard.repository.InitiativeRepository;
import com.eljo.agileboard.repository.UserRepository;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Eljo.George on 10/6/2018.
 */
@Configuration
public class GraphQLConfig {

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
