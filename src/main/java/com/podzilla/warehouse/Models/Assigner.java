package com.podzilla.warehouse.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Assignments")
public class Assigner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private Long orderId;

    private String courierId;

    private AssignmentStatus status;

    public Assigner() {}

    public Assigner(Long orderId, String courierId, AssignmentStatus status) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.status = status;
    }

    public Assigner(Long id, Long orderId, String courierId, AssignmentStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.courierId = courierId;
        this.status = status;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
