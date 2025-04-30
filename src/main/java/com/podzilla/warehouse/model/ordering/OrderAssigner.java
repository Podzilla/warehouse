package com.podzilla.warehouse.model.ordering;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderAssigner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "orderAssigner", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderAssignment> orderAssignments;
}
