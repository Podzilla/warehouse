package com.podzilla.warehouse.Events;

import com.podzilla.mq.EventPublisher;
import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.*;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.StockRepository;
import com.podzilla.warehouse.Services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderStockReservationEventHandler implements EventHandler<OrderStockReservationRequestedEvent> {
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final EventPublisher eventPublisher;


    @Override
    public void handle(OrderStockReservationRequestedEvent event) {
        boolean allAvailable = true;

        // Check if all items are available in stock
        for (OrderItem item : event.getItems()) {
            Stock stock = stockRepository.findById(UUID.fromString(item.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("Unknown product " + item.getProductId()));
            if (stock.getQuantity() < item.getQuantity()) {
                allAvailable = false;

                String reason = "Not enough stock for product " + item.getProductId();
                WarehouseOrderFulfillmentFailedEvent orderFulfillmentFailedEvent =
                        EventFactory.createWarehouseOrderFulfillmentFailedEvent(item.getProductId(), reason);
                eventPublisher.publishEvent(EventsConstants.WAREHOUSE_ORDER_FULFILLMENT_FAILED, orderFulfillmentFailedEvent);

                break;
            }
        }

        if (allAvailable) {
            List<ProductSnapshot> snapshots = new ArrayList<>();
            for (OrderItem item : event.getItems()) {
                UUID productId = UUID.fromString(item.getProductId());
                Stock stock = stockRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown product " + item.getProductId()));
                int newQuantity = stock.getQuantity() - item.getQuantity();

                stockService.updateStock(productId, null, newQuantity, null, null, null);

                snapshots.add(EventFactory.createProductSnapshot(item.getProductId(), newQuantity));
            }

            WarehouseStockReservedEvent stockReservedEvent = EventFactory.createWarehouseStockReservedEvent(event.getOrderId());
            eventPublisher.publishEvent(EventsConstants.WAREHOUSE_STOCK_RESERVED, stockReservedEvent);

            InventoryUpdatedEvent inventoryUpdatedEvent = EventFactory.createInventoryUpdatedEvent(snapshots);
            eventPublisher.publishEvent(EventsConstants.INVENTORY_UPDATED, inventoryUpdatedEvent);
        }
    }
}