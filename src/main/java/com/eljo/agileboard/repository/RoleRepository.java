package com.eljo.agileboard.repository;

import com.eljo.agileboard.domain.Role;
import com.eljo.agileboard.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Eljo.George on 10/14/2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
