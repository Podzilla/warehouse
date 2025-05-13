package com.podzilla.warehouse.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

//TODO: Ask the team what to pass to ERP for analytics

@Data
@AllArgsConstructor
public class ErpRestockEvent implements WarehouseEvent {
    private UUID productId;
    private int missingQuantity;
}
