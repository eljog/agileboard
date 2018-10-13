package com.eljo.agileboard;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgileBoardApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

}