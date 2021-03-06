package com.eljo.agileboard.graphql.input;

import com.eljo.agileboard.domain.Project;
import com.eljo.agileboard.domain.Story;
import com.eljo.agileboard.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryInput {
    private Long id;
    private String name;
    private String details;
    private Long ownerId;
    private String status;
    private Integer points;
    private Long projectId;

    public Story convertToStory() {
        Story story = new Story();
        story.setOwner(new User(this.ownerId));
        story.setDetails(this.details);
        story.setName(this.name);
        story.setId(this.id);
        story.setStatus(this.status);
        story.setProject(new Project(this.projectId));
        if(this.points != null) {
            story.setPoints(this.points);
        }

        return story;
    }
}
