package com.podzilla.warehouse.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table()
public class PackagedOrders {
    @Id
    private UUID orderId;

    private UUID packagerId;

    @CreationTimestamp
    private LocalDateTime packagedAt;

    public PackagedOrders(UUID orderId, UUID packagerId, LocalDateTime packagedAt) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.packagedAt = packagedAt;
    }

    public PackagedOrders(UUID orderId, UUID packagerId) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.packagedAt = LocalDateTime.now();
    }
}