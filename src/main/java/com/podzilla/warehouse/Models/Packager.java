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
    @Column(nullable = false)
    private boolean isActive = true;

    public Packager(String name) {
        this.name = name;
    }
    public Packager(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }
}
