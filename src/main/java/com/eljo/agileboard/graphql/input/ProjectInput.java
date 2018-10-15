package com.eljo.agileboard.graphql.input;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Eljo.George on 10/13/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInput {
    private Long id;
    private String name;
    private String description;

    public Project convertToProject() {
        return new Project(this.id, this.name, this.description);
    }
}
