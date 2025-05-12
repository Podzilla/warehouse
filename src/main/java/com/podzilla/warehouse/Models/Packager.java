package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Packagers")
public class Packager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
