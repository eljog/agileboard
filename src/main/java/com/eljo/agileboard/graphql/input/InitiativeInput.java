package com.eljo.agileboard.graphql.input;

import com.eljo.agileboard.domain.Initiative;
import com.eljo.agileboard.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@NoArgsConstructor
@Getter
@Setter
public class InitiativeInput {
    private Long id;
    private String name;
    private String details;
    private long ownerId;
    private String status;

    public Initiative convertToInitiative() {
        Initiative initiative = new Initiative();
        initiative.setOwner(new User(this.ownerId));
        initiative.setDetails(this.details);
        initiative.setName(this.name);
        initiative.setId(this.id);
        initiative.setStatus(this.status);

        return initiative;
    }
}
