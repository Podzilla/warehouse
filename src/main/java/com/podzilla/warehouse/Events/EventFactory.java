package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class EventFactory {

    /**
     * Creates a ProductEvent.
     *
     * @param productId         The product ID
     * @param name              The product name
     * @param category          The product category
     * @param cost              The product cost
     * @param lowStockThreshold The low stock threshold
     * @return A new ProductEvent instance
     */
    public static ProductCreatedEvent createProductEvent(
            UUID productId,
            String name,
            String category,
            double cost,
            int lowStockThreshold) {
        BigDecimal costBigDecimal = BigDecimal.valueOf(cost);
        return new ProductCreatedEvent(productId.toString(), name, category, costBigDecimal, lowStockThreshold);
    }

    /**
     * Creates an OrderPlacedEvent.
     *
     * @param orderId     The order ID
     * @param customerId  The customer ID
     * @param totalAmount The total amount of the order
     * @param items       The list of order items
     * @param address     The delivery address
     * @return A new OrderPlacedEvent instance
     */
    public static OrderPlacedEvent createOrderPlacedEvent(
            UUID orderId,
            String customerId,
            List<OrderItem> items,
            DeliveryAddress address,
            double totalAmount,
            double orderLatitude,
            double orderLongitude,
            String signature,
            ConfirmationType confirmationType) {
        BigDecimal totalAmountBigDecimal = BigDecimal.valueOf(totalAmount);
        return new OrderPlacedEvent(orderId.toString(),
                customerId,
                items,
                address,
                totalAmountBigDecimal,
                orderLatitude,
                orderLongitude,
                signature,
                confirmationType
        );
    }

    /**
     * Creates a WarehouseStockReservedEvent.
     *
     * @param orderId The product ID
     * @return A new WarehouseStockReservedEvent instance
     */
    public static WarehouseStockReservedEvent createWarehouseStockReservedEvent(String orderId) {
        return new WarehouseStockReservedEvent(orderId);
    }


    /**
     * Creates an OrderPackagedEvent.
     *
     * @param orderId The order ID
     * @return A new OrderPackagingCompletedEvent instance
     */
    public static OrderPackagedEvent createOrderPackagedEvent(
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
    public static OrderAssignedToCourierEvent createOrderAssignedToCourierEvent(
            UUID orderId,
            UUID courierId,
            BigDecimal totalAmount,
            double orderLatitude,
            double orderLongitude,
            String signature,
            ConfirmationType confirmationType) {
        return new OrderAssignedToCourierEvent(
                orderId.toString(),
                courierId.toString(),
                totalAmount,
                orderLatitude,
                orderLongitude,
                signature,
                confirmationType
        );
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

    /**
     * Creates an WarehouseOrderFulfillmentFailedEvent.
     *
     * @param orderId The product ID
     * @param reason  The missing quantity
     * @return A new WarehouseOrderFulfillmentFailedEvent instance
     */
    public static WarehouseOrderFulfillmentFailedEvent createWarehouseOrderFulfillmentFailedEvent(
            String orderId,
            String reason) {
        return new WarehouseOrderFulfillmentFailedEvent(orderId, reason);
    }


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
    public static OrderPlacedEvent createOrderPlacedEvent(
            UUID orderId,
            UUID customerId,
            List<OrderItem> items,
            DeliveryAddress deliveryAddress,
            double totalAmount,
            double orderLatitude,
            double orderLongitude,
            String signature,
            ConfirmationType confirmationType
    ) {
        BigDecimal totalAmountBigDecimal = BigDecimal.valueOf(totalAmount);
        return new OrderPlacedEvent(
                orderId.toString(),
                customerId.toString(),
                items,
                deliveryAddress,
                totalAmountBigDecimal,
                orderLatitude,
                orderLongitude,
                signature,
                confirmationType
        );
    }


    /**
     * Helper method to create a DeliveryAddress for OrderPlacedEvent.
     *
     * @param street  The street address
     * @param city    The city
     * @param state   The state
     * @param zipCode The zip code
     * @param country The country
     * @return A new DeliveryAddress instance
     */
    public static DeliveryAddress createDeliveryAddress(
            String street,
            String city,
            String state,
            String zipCode,
            String country) {
        return new DeliveryAddress(street, city, state, zipCode, country);
    }

    public static InventoryUpdatedEvent createInventoryUpdatedEvent(List<ProductSnapshot> products) {
        return new InventoryUpdatedEvent(products);
    }

    /**
     * Helper method to create a ProductSnapshot for InventorySnapshotEvent.
     *
     * @param productId   The product ID
     * @param newQuantity The current quantity
     * @return A new ProductSnapshot instance
     */
    public static ProductSnapshot createProductSnapshot(
            String productId,
            int newQuantity) {
        return new ProductSnapshot(productId, newQuantity);
    }
}