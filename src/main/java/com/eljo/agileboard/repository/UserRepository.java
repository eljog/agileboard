package com.eljo.agileboard.repository;

import com.eljo.agileboard.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
