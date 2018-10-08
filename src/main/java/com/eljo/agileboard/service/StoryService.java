package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.StoryRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    public Story getStory(long id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        if (storyOptional.isPresent()) {
            return storyOptional.get();
        }
        return null;
    }

    public Iterable<Story> getStories() {
        return storyRepository.findAll();
    }

    public Story createStory(Story story) throws InvalidRecordExeption {
        try {
            isValidStory(story);
        } catch (InvalidUserException e) {
            throw new InvalidRecordExeption("Invalid Story record!", e);
        }
        return storyRepository.save(story);
    }

    private void isValidStory(Story story) throws InvalidRecordExeption, InvalidUserException {
        if (StringUtils.isEmpty(story.getName()) || story.getOwner() == null) {
            throw new InvalidRecordExeption("One or more of the following mandatory fields are empty: Name, Owner");
        }

        Optional<User> userOptional = userRepository.findById(story.getOwner().getId());
        if (userOptional.isPresent()) {
            story.setOwner(userOptional.get());
        } else {
            throw new InvalidUserException("No User exists with ID: " + story.getOwner().getId());
        }

    }
}
