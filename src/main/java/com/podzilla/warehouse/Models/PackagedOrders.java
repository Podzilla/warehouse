package com.podzilla.warehouse.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table()
public class PackagedOrders {
    @Id
    private Long orderId;

    private Long packagerId;
    
    public PackagedOrders(Long orderId, Long packagerId) {
        this.orderId = orderId;
        this.packagerId = packagerId;
    }

    public PackagedOrders() {}
}
