package com.podzilla.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String status;

//    @ManyToOne
//    @JoinColumn(name = "courier_id")
//    @ToString.Exclude
//    private Courier courier;
}
