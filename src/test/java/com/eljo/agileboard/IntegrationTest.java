package com.eljo.agileboard;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by Eljo.George on 10/6/2018.
 */
@RunWith(SpringRunner.class)
@GraphQLTest
@EnableAutoConfiguration
public class IntegrationTest {

    /* Look at
    https://github.com/graphql-java-kickstart/graphql-spring-boot/blob/master/example-graphql-tools/src/test/java/com/oembedler/moon/graphql/boot/GraphQLToolsSampleApplicationTest.java
     */

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void nativeTestGraphqlEndPoint() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/test_query.graphql");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Assertions.assertThat(response.get("$.data.getUsers[0].name")).isEqualTo("Eljo George");
    }
}
