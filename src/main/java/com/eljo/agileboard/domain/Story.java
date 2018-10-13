package com.eljo.agileboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String storyNumber;

    private String name;
    private String details;

    @OneToOne
    private User owner;
    private String status;

    public Story(String name, String details, User owner, String status) {
        this.name = name;
        this.details = details;
        this.owner = owner;
        this.status = status;
    }
}
