package com.podzilla.warehouse.Events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderPackagingCompletedEvent implements WarehouseEvent {
    private LocalDateTime timestamp;
    private UUID orderId;
    private UUID packagedByUserId;
}
