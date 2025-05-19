package com.podzilla.warehouse.Models;

import com.podzilla.mq.events.ConfirmationType;
import com.podzilla.mq.events.DeliveryAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table()
public class PackagedOrders {
    @Id
    private UUID orderId;
    private UUID packagerId;
    @OneToMany(mappedBy = "packagedOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> items;

    // @Column(nullable = false)
    private DeliveryAddress deliveryAddress;
    // @Column(nullable = false)
    private BigDecimal totalAmount;
    // @Column(nullable = false)
    private double orderLatitude;
    // @Column(nullable = false)
    private double orderLongitude;
    // @Column(nullable = false)
    private String signature;
    // @Column(nullable = false)
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
    public PackagedOrders(UUID orderId, UUID packagerId, List<Stock> items, DeliveryAddress deliveryAddress,
                          BigDecimal totalAmount, double orderLatitude, double orderLongitude, String signature,
                          ConfirmationType confirmationType) {
        this.orderId = orderId;
        this.packagerId = packagerId;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.orderLatitude = orderLatitude;
        this.orderLongitude = orderLongitude;
        this.signature = signature;
        this.confirmationType = confirmationType;
    }
}