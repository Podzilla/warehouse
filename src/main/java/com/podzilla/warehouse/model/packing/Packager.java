package com.podzilla.warehouse.model.packing;

import com.podzilla.warehouse.model.ordering.OrderedItem;
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
public class Packager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "packager", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderedItem> packedOrders;
}
