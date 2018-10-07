package com.eljo.agileboard.graphql;

import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MutationComponentTest {

    @Mock
    private UserService userService;

    @Mock
    private InitiativeService initiativeService;

    @Autowired
    @InjectMocks
    private MutationComponent mutationComponent;

    @Test
    public void createUser() throws Exception {
    }

    @Test
    public void createInitiative() throws Exception {
    }

}