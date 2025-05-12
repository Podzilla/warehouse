package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private Long assignerId;

    @Column()
    private UUID courierId;

    @Column()
    private LocalDateTime assignedAt;

    public AssignedOrders(UUID orderId, Long assignerId, UUID courierId) {
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
        this.assignedAt = LocalDateTime.now();
    }


    public AssignedOrders(UUID orderId, UUID taskId, UUID courierId, LocalDateTime assignedAt) {
        this.orderId = orderId;
        this.assignerId = taskId;
        this.courierId = courierId;
        this.assignedAt = assignedAt;
    }
}
