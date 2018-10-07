package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws InvalidUserException {
        isValid(user);
        return userRepository.save(user);
    }

    private void isValid(User user) throws InvalidUserException {
        if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getName()) ||
                StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new InvalidUserException("One or more of the following mandatory fields are empty: Name, Email, Username, Password");
        }
    }
}
