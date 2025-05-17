package com.podzilla.warehouse.Events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventFactory {

    /**
     * Creates a ProductEvent.
     *
     * @param timestamp The timestamp of the event
     * @param id The product ID
     * @param name The product name
     * @param category The product category
     * @param cost The product cost
     * @param lowStockThreshold The low stock threshold
     * @return A new ProductEvent instance
     */
    public static ProductEvent createProductEvent(
            LocalDateTime timestamp,
            UUID id,
            String name,
            String category,
            double cost,
            int lowStockThreshold) {
        return new ProductEvent(timestamp, id, name, category, cost, lowStockThreshold);
    }

    /**
     * Creates an OrderPlacedEvent.
     *
     * @param orderId The order ID
     * @param items The list of order items
     * @return A new OrderPlacedEvent instance
     */
    public static OrderPlacedEvent createOrderPlacedEvent(
            UUID orderId,
            List<OrderPlacedEvent.OrderItem> items) {
        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setOrderId(orderId);
        event.setItems(items);
        return event;
    }

    /**
     * Creates an OrderPackagingCompletedEvent.
     *
     * @param timestamp The timestamp of the event
     * @param orderId The order ID
     * @param packagedByUserId The ID of the user who packaged the order
     * @return A new OrderPackagingCompletedEvent instance
     */
    public static OrderPackagingCompletedEvent createOrderPackagingCompletedEvent(
            LocalDateTime timestamp,
            UUID orderId,
            UUID packagedByUserId) {
        return new OrderPackagingCompletedEvent(timestamp, orderId, packagedByUserId);
    }

    /**
     * Creates an OrderAssignedEvent.
     *
     * @param timestamp The timestamp of the event
     * @param orderId The order ID
     * @param taskId The task ID
     * @param courierId The courier ID
     * @return A new OrderAssignedEvent instance
     */
    public static OrderAssignedEvent createOrderAssignedEvent(
            LocalDateTime timestamp,
            UUID orderId,
            UUID taskId,
            UUID courierId) {
        return new OrderAssignedEvent(timestamp, orderId, taskId, courierId);
    }

    /**
     * Creates an InventorySnapshotEvent.
     *
     * @param timestamp The timestamp of the event
     * @param warehouseId The warehouse ID
     * @param products The list of product snapshots
     * @return A new InventorySnapshotEvent instance
     */
    public static InventorySnapshotEvent createInventorySnapshotEvent(
            LocalDateTime timestamp,
            UUID warehouseId,
            List<InventorySnapshotEvent.ProductSnapshot> products) {
        return new InventorySnapshotEvent(timestamp, warehouseId, products);
    }

    /**
     * Creates an ErpRestockEvent.
     *
     * @param productId The product ID
     * @param missingQuantity The missing quantity
     * @return A new ErpRestockEvent instance
     */
    public static ErpRestockEvent createErpRestockEvent(
            UUID productId,
            int missingQuantity) {
        return new ErpRestockEvent(productId, missingQuantity);
    }

    /**
     * Helper method to create an OrderItem for OrderPlacedEvent.
     *
     * @param productId The product ID
     * @param quantity The quantity
     * @return A new OrderItem instance
     */
    public static OrderPlacedEvent.OrderItem createOrderItem(
            UUID productId,
            int quantity) {
        OrderPlacedEvent.OrderItem item = new OrderPlacedEvent.OrderItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }

    /**
     * Helper method to create a ProductSnapshot for InventorySnapshotEvent.
     *
     * @param productId The product ID
     * @param currentQuantity The current quantity
     * @return A new ProductSnapshot instance
     */
    public static InventorySnapshotEvent.ProductSnapshot createProductSnapshot(
            UUID productId,
            int currentQuantity) {
        return new InventorySnapshotEvent.ProductSnapshot(productId, currentQuantity);
    }
}