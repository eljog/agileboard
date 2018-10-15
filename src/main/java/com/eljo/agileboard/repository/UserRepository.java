package com.eljo.agileboard.repository;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findByProject(Project project);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
