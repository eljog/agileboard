package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.InitiativeRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Service
public class InitiativeService {

    private InitiativeRepository initiativeRepository;
    private UserRepository userRepository;

    public InitiativeService(InitiativeRepository initiativeRepository, UserRepository userRepository) {
        this.initiativeRepository = initiativeRepository;
        this.userRepository = userRepository;
    }

    public Initiative getInitiative(long id) {
        Optional<Initiative> initiativeOptional = initiativeRepository.findById(id);
        if (initiativeOptional.isPresent()) {
            return initiativeOptional.get();
        }
        return null;
    }

    public Iterable<Initiative> getInitiatives() {
        return initiativeRepository.findAll();
    }

    public Initiative createInitiative(Initiative initiative) throws InvalidRecordExeption {
        try {
            User owner = getValidatedOwner(initiative.getOwner());
            initiative.setOwner(owner);
            isValidInitiative(initiative);
            return initiativeRepository.save(initiative);
        } catch (InvalidUserException e) {
            throw new InvalidRecordExeption("Invalid Initiative record.", e);
        }


    }

    private void isValidInitiative(Initiative initiative) throws InvalidRecordExeption {
        if (StringUtils.isEmpty(initiative.getName())) {
            throw new InvalidRecordExeption("One or more of the following mandatory fields are empty: Name");
        }
    }

    private User getValidatedOwner(User owner) throws InvalidUserException {
        Optional<User> userOptional = userRepository.findById(owner.getId());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new InvalidUserException("Invalid Owner: " + owner.getId());
    }
}
