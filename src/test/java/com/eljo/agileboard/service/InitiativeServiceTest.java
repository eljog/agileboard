package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.InitiativeRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class InitiativeServiceTest {

    @Mock
    private InitiativeRepository initiativeRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private InitiativeService initiativeService;

    private Initiative initiative;

    @Before
    public void setUp() throws Exception {
        this.initiative = new Initiative("name", "details", new User(), "status");
    }

    @Test
    public void getInitiative() throws Exception {
        when(initiativeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(initiative));

        Initiative returnedInitiative = initiativeService.getInitiative(1);

        verify(initiativeRepository, times(1)).findById(anyLong());
        assertThat("name").isEqualTo(returnedInitiative.getName());
    }

    @Test
    public void getInitiative_notFound() throws Exception {
        when(initiativeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Initiative returnedInitiative = initiativeService.getInitiative(1);

        verify(initiativeRepository, times(1)).findById(anyLong());
        assertNull(returnedInitiative);
    }

    @Test
    public void getInitiatives() throws Exception {
        when(initiativeRepository.findAll()).thenReturn(Collections.singletonList(initiative));

        Iterable<Initiative> initiativeIterable = initiativeService.getInitiatives();

        verify(initiativeRepository, times(1)).findAll();
        assertThat("name").isEqualTo(initiativeIterable.iterator().next().getName());
    }

    @Test
    public void createInitiative() throws Exception {
        when(initiativeRepository.save(any())).thenReturn(initiative);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(new User()));

        Initiative savedInitiative = initiativeService.createInitiative(initiative);

        verify(initiativeRepository, times(1)).save(any());
        assertThat("details").isEqualTo(savedInitiative.getDetails());
    }

    @Test
    public void createInitiative_invalidOwner() throws Exception {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Initiative savedInitiative= null;
        try {
            savedInitiative = initiativeService.createInitiative(initiative);
        } catch (InvalidRecordExeption e) {
            if(e.getCause() instanceof InvalidUserException) {
                assertTrue(true);
            }
            else {
                assertTrue(false);
            }
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(initiativeRepository, never()).save(any());
        assertNull(savedInitiative);
    }

    @Test
    public void createInitiative_missingMandatoryField() throws Exception {
        initiative.setName(null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(new User()));

        Initiative savedInitiative= null;
        try {
            savedInitiative = initiativeService.createInitiative(initiative);
        } catch (InvalidRecordExeption e) {
            if("One or more of the following mandatory fields are empty: Name".equals(e.getMessage())) {
                assertTrue(true);
            }
            else {
                assertTrue(false);
            }
        } catch (Exception e) {
            assertTrue(false);
        }

        verify(initiativeRepository, never()).save(any());
        assertNull(savedInitiative);
    }

}