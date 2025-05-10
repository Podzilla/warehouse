package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table()
public class PackagedOrders {
    @Id
    private UUID orderId;

    private UUID packagerId;

    private LocalDateTime packagedAt;

    public PackagedOrders(UUID orderId, UUID packagerId, LocalDateTime packagedAt) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.packagedAt = packagedAt;
    }

    public PackagedOrders() {}
}