package com.eljo.agileboard.repository;

import com.eljo.agileboard.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
}
