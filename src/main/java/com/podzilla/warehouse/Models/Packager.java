package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Packagers")
public class Packager {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column
    private boolean active = true;

    public Packager(String name) {
        this.name = name;
    }
    public Packager(String name, boolean active) {
        this.name = name;
        this.active = active;
    }
}
