package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.StoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
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
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public Story getStory(Long id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        if (storyOptional.isPresent()) {
            return storyOptional.get();
        }
        return null;
    }

    public Iterable<Story> getStories() {
        return storyRepository.findAll();
    }

    public Iterable<Story> fetchStoriesByProject(Project project) throws InvalidRecordExeption {
        return storyRepository.findByProject(project);
    }

    public Story createOrUpdateStory(Story story) throws InvalidRecordExeption {
        try {
            isValidStory(story);
        } catch (InvalidUserException e) {
            throw new InvalidRecordExeption("Invalid Story record!", e);
        }

        if (story.getId() != null) {
            return this.updateStory(story);
        }

        return createStory(story);
    }

    private Story createStory(Story story) {
        return saveStory(story);
    }

    private Story updateStory(Story story) throws InvalidRecordExeption {
        Optional<Story> storyOptional = storyRepository.findById(story.getId());
        if (!storyOptional.isPresent()) {
            throw new InvalidRecordExeption("No Story exists with given id: " + story.getId());
        }
        Story existingStory = storyOptional.get();
        this.updateValues(existingStory, story);
        return saveStory(existingStory);
    }

    private Story updateValues(Story existingStory, Story story) {
        if (story.getName() != null) {
            existingStory.setName(story.getName());
        }

        if (story.getProject() != null) {
            existingStory.setProject(story.getProject());
        }

        if (story.getOwner() != null) {
            existingStory.setOwner(story.getOwner());
        }

        if (story.getDetails() != null) {
            existingStory.setDetails(story.getDetails());
        }

        if (story.getPoints() != null) {
            existingStory.setPoints(story.getPoints());
        }

        if (story.getStatus() != null) {
            existingStory.setStatus(story.getStatus());
        }

        return existingStory;
    }

    @NotNull
    private Story saveStory(Story story) {
        return storyRepository.save(story);
    }

    private void isValidStory(Story story) throws InvalidRecordExeption, InvalidUserException {
        if (story == null) {
            throw new InvalidRecordExeption("Invalid Story record!");
        }

        if (StringUtils.isEmpty(story.getName()) || story.getOwner() == null) {
            throw new InvalidRecordExeption("One or more of the following mandatory fields are empty: Name, Owner");
        }

        User owner = userService.getUser(story.getOwner().getId());
        if (owner != null) {
            story.setOwner(owner);
        } else {
            throw new InvalidUserException("No User exists with ID: " + story.getOwner().getId());
        }

        Project project = projectService.getProject(story.getProject().getId());
        if (project != null) {
            story.setProject(project);
        } else {
            throw new InvalidRecordExeption("No Project exists with ID: " + story.getProject().getId());
        }

    }
}
