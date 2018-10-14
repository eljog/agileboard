package com.eljo.agileboard.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.graphql.input.InitiativeInput;
import com.eljo.agileboard.graphql.input.ProjectInput;
import com.eljo.agileboard.graphql.input.StoryInput;
import com.eljo.agileboard.graphql.input.UserInput;
import com.eljo.agileboard.service.InitiativeService;
import com.eljo.agileboard.service.ProjectService;
import com.eljo.agileboard.service.StoryService;
import com.eljo.agileboard.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@Component
public class MutationComponent implements GraphQLMutationResolver {

    private InitiativeService initiativeService;
    private UserService userService;
    private StoryService storyService;
    private ProjectService projectService;

    public MutationComponent(InitiativeService initiativeService, UserService userService, StoryService storyService, ProjectService projectService) {
        this.initiativeService = initiativeService;
        this.userService = userService;
        this.storyService = storyService;
        this.projectService = projectService;
    }

    public User createUser(UserInput userInput) throws InvalidUserException {
        User user = userInput.convertToUser();
        return userService.createUser(user);
    }

    public User addProjectToUser(Long projectId, Long userId) throws InvalidRecordExeption, InvalidUserException {
        return userService.addProjectToUser(new Project(projectId), new User(userId));
    }

    public Initiative createInitiative(InitiativeInput initiativeInput) throws InvalidRecordExeption {
        Initiative initiative = initiativeInput.convertToInitiative();
        return initiativeService.createInitiative(initiative);
    }

    public Story createOrUpdateStory(StoryInput storyInput) throws InvalidRecordExeption {
        Story story = storyInput.convertToStory();
        return storyService.createOrUpdateStory(story);
    }

    public Story createStory(StoryInput storyInput) throws InvalidRecordExeption {
        return this.createOrUpdateStory(storyInput);
    }

    public Story updateStory(StoryInput storyInput) throws InvalidRecordExeption {
        return this.createOrUpdateStory(storyInput);
    }

    public Project createOrUpdateProject(ProjectInput projectInput) throws InvalidRecordExeption, InvalidUserException {
        Project project = projectInput.convertToProject();
        return projectService.createOrUpdateProject(project);
    }

    public Project createProject(ProjectInput projectInput) throws InvalidRecordExeption, InvalidUserException {
        return this.createOrUpdateProject(projectInput);
    }

    public Project updateProject(ProjectInput projectInput) throws InvalidRecordExeption, InvalidUserException {
        return this.createOrUpdateProject(projectInput);
    }
}
