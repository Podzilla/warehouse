package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Assigners")
public class Assigner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column
    private boolean isActive = true;


    public Assigner(String name) {
        this.name = name;
    }

    public Assigner(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

}
