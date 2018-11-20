package com.eljo.agileboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eljo.George on 10/13/2018.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @JoinColumn(updatable = false)
    @OneToOne
    @JsonIgnore
    private User createdBy;

    @Column(updatable = false)
    private Date createdOn;

    public Project(String name, String description, User createdBy, Date createdOn) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }

    public Project(Long projectId) {
        this.id = projectId;
    }

    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
