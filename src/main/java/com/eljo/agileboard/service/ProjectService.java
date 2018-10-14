package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidRecordExeption;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.repository.ProjectRepository;
import com.eljo.agileboard.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Eljo.George on 10/13/2018.
 */

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private UserService userService;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Project getProject(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        }
        return null;
    }

    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project createOrUpdateProject(Project project) throws InvalidRecordExeption, InvalidUserException {
        try {
            isValidProject(project);
        } catch (InvalidUserException e) {
            throw new InvalidRecordExeption("Invalid Project record!", e);
        }

        if (project.getId() != null) {
            return this.updateProject(project);
        }

        return createProject(project);
    }

    private Project createProject(Project project) throws InvalidRecordExeption, InvalidUserException {
        project.setCreatedOn(new Date());
        Project savedProject = saveProject(project);
        userService.addProjectToUser(savedProject, project.getCreatedBy());
        return savedProject;
    }

    private Project updateProject(Project project) throws InvalidRecordExeption {
        if (!projectRepository.findById(project.getId()).isPresent()) {
            throw new InvalidRecordExeption("No Project exists with given id: " + project.getId());
        }

        return saveProject(project);
    }

    private Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    void isValidProject(Project project) throws InvalidRecordExeption, InvalidUserException {
        if (project == null) {
            throw new InvalidRecordExeption("Invalid Project record!");
        }

        if (StringUtils.isEmpty(project.getName()) || project.getCreatedBy() == null) {
            throw new InvalidRecordExeption("One or more of the following mandatory fields are empty: Name, CreatedBy");
        }

        Optional<User> userOptional = userRepository.findById(project.getCreatedBy().getId());
        if (userOptional.isPresent()) {
            project.setCreatedBy(userOptional.get());
        } else {
            throw new InvalidUserException("No User exists with ID: " + project.getCreatedBy().getId());
        }

    }

}
