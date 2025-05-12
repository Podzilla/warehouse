package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column
    private String phoneNumber;

    @Column
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Manager(String name, String email, String department, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.phoneNumber = phoneNumber;
    }

    public Manager(String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }
}