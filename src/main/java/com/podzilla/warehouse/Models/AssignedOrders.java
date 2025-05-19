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

@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "Assignments")
public class AssignedOrders {
    @Id
    private UUID orderId;

    @Column()
    private UUID assignerId;

    @Column()
    private UUID courierId;

    @Column()
    @CreationTimestamp
    private LocalDateTime assignedAt;

    @Column(nullable = false)
    @OneToMany(mappedBy = "assignedOrder")
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

    public AssignedOrders(UUID orderId, UUID assignerId, UUID courierId, List<Stock> items,
                         DeliveryAddress deliveryAddress, BigDecimal totalAmount, double orderLatitude,
                         double orderLongitude, String signature, ConfirmationType confirmationType) {
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
        this.assignedAt = LocalDateTime.now();
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.orderLatitude = orderLatitude;
        this.orderLongitude = orderLongitude;
        this.signature = signature;
        this.confirmationType = confirmationType;
    }
    
    public AssignedOrders(UUID orderId, UUID assignerId, UUID courierId, LocalDateTime assignedAt,
                         List<Stock> items, DeliveryAddress deliveryAddress, BigDecimal totalAmount,
                         double orderLatitude, double orderLongitude, String signature, ConfirmationType confirmationType) {
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
        this.assignedAt = assignedAt;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.orderLatitude = orderLatitude;
        this.orderLongitude = orderLongitude;
        this.signature = signature;
        this.confirmationType = confirmationType;
    }
}
