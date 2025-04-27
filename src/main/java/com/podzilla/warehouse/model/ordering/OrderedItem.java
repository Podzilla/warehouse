package com.podzilla.warehouse.model.ordering;

import com.podzilla.warehouse.model.Item;
import com.podzilla.warehouse.model.packing.Packager;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @ToString.Exclude
    private Item item;

    @ManyToOne
    @JoinColumn(name = "packager_id")
    @ToString.Exclude
    private Packager packager;
}
