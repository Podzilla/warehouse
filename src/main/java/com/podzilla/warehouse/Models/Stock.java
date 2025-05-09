package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer threshold;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String category;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Stock() {}

    public Stock(String name, Integer quantity, Integer threshold) {
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public Stock(String name, Integer quantity, Integer threshold, String category, double cost) {
        this.name = name;
        this.quantity = quantity;
        this.threshold = threshold;
        this.category = category;
        this.price = cost;
    }

    public void setCost(Double cost) {
        this.price = cost;
    }

    public double getCost() {
        return this.price;
    }
}