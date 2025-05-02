package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "Assignments")
public class AssignedOrders {
    @Id
    private Long orderId;

    @Column()
    private Long assignerId;

    @Column()
    private Long courierId;


    public AssignedOrders() {}

    public AssignedOrders(Long orderId, Long assignerId, Long courierId) {
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
    }
    
    
}
