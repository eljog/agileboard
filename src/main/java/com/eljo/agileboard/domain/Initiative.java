package com.eljo.agileboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

/**
 * Created by Eljo.George on 10/7/2018.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Initiative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String details;

    @OneToOne
    private User owner;
    private String status;

    public Initiative(String name, String details, User owner, String status) {
        this.name = name;
        this.details = details;
        this.owner = owner;
        this.status = status;
    }
}
