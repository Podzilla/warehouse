package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table()
public class PackagedOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    private UUID packagerId;

    @CreationTimestamp
    private LocalDateTime packagedAt;

    public PackagedOrders(UUID orderId, UUID packagerId, LocalDateTime packagedAt) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.packagedAt = packagedAt;
    }

    public PackagedOrders(UUID packagerId) {
        this.packagerId = packagerId;
        this.packagedAt = LocalDateTime.now();
    }

}