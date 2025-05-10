package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Assigners")
public class Assigner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;
    @Column
    private boolean isActive = true;

    public Assigner(String name) {
        this.name = name;
    }
    public Assigner(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Assigner(String name, String email, boolean isActive) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
    }

}
