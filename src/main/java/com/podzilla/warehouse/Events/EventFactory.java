package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class EventFactory {

    /**
     * Creates a ProductEvent.
     *
     * @param productId         The product Id
     * @param name              The product name
     * @param category          The product category
     * @param cost              The product cost
     * @param lowStockThreshold The low stock threshold
     * @return A new ProductEvent instance
     */
    public static ProductCreatedEvent createProductEvent(
            String productId,
            String name,
            String category,
            BigDecimal cost,
            Integer lowStockThreshold) {
        return new ProductCreatedEvent(productId, name, category, cost, lowStockThreshold);
    }

    /**
     * Creates an OrderPackagedEvent.
     *
     * @param orderId The order ID
     * @return A new OrderPackagingCompletedEvent instance
     */
    public static OrderPackagedEvent createOrderPackagingCompletedEvent(
            UUID orderId) {
        return new OrderPackagedEvent(orderId.toString());
    }

    /**
     * Creates an OrderAssignedEvent.
     *
     * @param orderId   The order ID
     * @param courierId The courier ID
     * @return A new OrderAssignedEvent instance
     */
    public static OrderAssignedToCourierEvent createOrderAssignedEvent(
            UUID orderId,
            UUID courierId) {
        return new OrderAssignedToCourierEvent(orderId.toString(), courierId.toString());
    }
//
//    /**
//     * Creates an InventorySnapshotEvent.
//     *
//     * @param timestamp The timestamp of the event
//     * @param warehouseId The warehouse ID
//     * @param products The list of product snapshots
//     * @return A new InventorySnapshotEvent instance
//     */
//    public static InventorySnapshotEvent createInventorySnapshotEvent(
//            LocalDateTime timestamp,
//            UUID warehouseId,
//            List<InventorySnapshotEvent.ProductSnapshot> products) {
//        return new InventorySnapshotEvent(timestamp, warehouseId, products);
//    }
//
//    /**
//     * Creates an ErpRestockEvent.
//     *
//     * @param productId The product ID
//     * @param missingQuantity The missing quantity
//     * @return A new ErpRestockEvent instance
//     */
//    public static ErpRestockEvent createErpRestockEvent(
//            UUID productId,
//            int missingQuantity) {
//        return new ErpRestockEvent(productId, missingQuantity);
//    }
//

    /**
     * Helper method to create an OrderItem for OrderPlacedEvent.
     *
     * @param productId    The product ID
     * @param quantity     The quantity
     * @param pricePerUnit The price per unit
     * @return A new OrderItem instance
     */
    public static OrderItem createOrderItem(UUID productId, int quantity, BigDecimal pricePerUnit) {
        return new OrderItem(productId.toString(), quantity, pricePerUnit);
    }

    /**
     * Creates an InventorySnapshotEvent.
     *
     * @param orderId         The order id
     * @param customerId      The customer id
     * @param totalAmount     The total amount
     * @param items           List of Order Items
     * @param deliveryAddress The delivery Address
     * @return OrderPlacedEvent
     */
    public static OrderPlacedEvent createOrderPlacedEvent(String orderId, String customerId, BigDecimal totalAmount, List<OrderItem> items, DeliveryAddress deliveryAddress) {
        return new OrderPlacedEvent(orderId, customerId, totalAmount, items, deliveryAddress);
    }

//
//    /**
//     * Helper method to create a ProductSnapshot for InventorySnapshotEvent.
//     *
//     * @param productId The product ID
//     * @param currentQuantity The current quantity
//     * @return A new ProductSnapshot instance
//     */
//    public static InventorySnapshotEvent.ProductSnapshot createProductSnapshot(
//            UUID productId,
//            int currentQuantity) {
//        return new InventorySnapshotEvent.ProductSnapshot(productId, currentQuantity);
//    }
}