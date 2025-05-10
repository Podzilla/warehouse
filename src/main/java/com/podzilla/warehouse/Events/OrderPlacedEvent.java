package com.podzilla.warehouse.Events;


import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class OrderPlacedEvent {
    private UUID orderId;
    private List<OrderItem> items;

    @Data
    public static class OrderItem {
        private UUID productId;
        private int quantity;
    }
}