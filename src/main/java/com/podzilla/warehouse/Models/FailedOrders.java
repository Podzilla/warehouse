package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
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
public class FailedOrders {
    
    public enum Status {
        PENDING,
        REASSIGNED,
        FAILED
    }
    
    @Id
    private UUID orderId;
    
    private UUID courierId;
    
    private String reason;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    public FailedOrders(UUID orderId, UUID courierId, String reason) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.reason = reason;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
    }
    
    public FailedOrders(UUID orderId, UUID courierId, String reason, Status status) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.reason = reason;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}