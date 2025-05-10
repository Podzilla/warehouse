package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
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


    public AssignedOrders() {}

    public AssignedOrders(UUID orderId, UUID assignerId, UUID courierId) {
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
    }


    public AssignedOrders(UUID orderId, UUID taskId, UUID courierId, LocalDateTime now) {
        this.orderId = orderId;
        this.assignerId = taskId;
        this.courierId = courierId;
        this.assignedAt = now;
    }
}
