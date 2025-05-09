package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "Assigners")
public class Assigner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
