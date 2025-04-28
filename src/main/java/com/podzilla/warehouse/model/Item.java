package com.podzilla.warehouse.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stockQuantity;

//    @ManyToOne
//    @JoinColumn(name = "warehouse_manager_id", nullable = false)
//    @ToString.Exclude
//    private WarehouseManager warehouseManager;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderedItem> orderedItems;
}
