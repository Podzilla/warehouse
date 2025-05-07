package com.podzilla.warehouse.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Packeges")
public class Packager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
