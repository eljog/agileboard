package com.eljo.agileboard.repository;

import com.eljo.agileboard.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Eljo.George on 10/13/2018.
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
