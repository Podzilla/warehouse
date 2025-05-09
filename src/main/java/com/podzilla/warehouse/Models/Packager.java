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
