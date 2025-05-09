package com.podzilla.warehouse.Events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InventorySnapshotEvent {
    private LocalDateTime timestamp;
    private UUID warehouseId;
    private List<ProductSnapshot> products;

    @Data
    @AllArgsConstructor
    public static class ProductSnapshot {
        private UUID productId;
        private int currentQuantity;
    }
}

