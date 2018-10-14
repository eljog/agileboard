package com.eljo.agileboard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @JoinColumn(updatable = false)
    @OneToOne
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
}
