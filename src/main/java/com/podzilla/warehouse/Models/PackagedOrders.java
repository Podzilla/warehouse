package com.podzilla.warehouse.Models;

import com.podzilla.mq.events.ConfirmationType;
import com.podzilla.mq.events.DeliveryAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
    @Column(nullable = false)
    @OneToMany(mappedBy = "packagedOrder")
    private List<Stock> items;

    @Column(nullable = false)
    private DeliveryAddress deliveryAddress;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    @Column(nullable = false)
    private double orderLatitude;
    @Column(nullable = false)
    private double orderLongitude;
    @Column(nullable = false)
    private String signature;
    @Column(nullable = false)
    private ConfirmationType confirmationType;

    @CreationTimestamp
    private LocalDateTime packagedAt;

    public PackagedOrders(UUID orderId, UUID packagerId, LocalDateTime packagedAt) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.packagedAt = packagedAt;
    }

    public PackagedOrders(UUID orderId) {
        this.orderId = orderId;
        this.packagedAt = LocalDateTime.now();

    }
}