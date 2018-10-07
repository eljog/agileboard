package com.eljo.agileboard;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Eljo.George on 10/6/2018.
 */

@GraphQLTest
@RunWith(SpringRunner.class)
public class IntegrationTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void nativeTestGraphqlEndPoint() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/test_query.graphql");
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.get("$.data.getUsers[0].name")).isEqualTo("Eljo George");
    }

}