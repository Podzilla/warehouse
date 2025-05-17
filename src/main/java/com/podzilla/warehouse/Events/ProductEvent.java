package com.podzilla.warehouse.Events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductEvent implements WarehouseEvent {
    private LocalDateTime timestamp;
    private UUID id;
    private String name;
    private String category;
    private double cost;
    private int lowStockThreshold;
}
