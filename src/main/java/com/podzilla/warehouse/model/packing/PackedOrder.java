package com.podzilla.warehouse.model.packing;

import com.podzilla.warehouse.model.ordering.OrderAssigner;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "order_assigner_id")
    @ToString.Exclude
    private OrderAssigner orderAssigner;
}
