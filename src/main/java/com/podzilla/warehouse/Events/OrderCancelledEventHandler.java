package com.podzilla.warehouse.Events;

import com.podzilla.mq.EventPublisher;
import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.OrderCancelledEvent;
import com.podzilla.mq.events.OrderItem;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.AssignedOrdersRepository;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import com.podzilla.warehouse.Repositories.StockRepository;
import com.podzilla.warehouse.Services.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderCancelledEventHandler implements EventHandler<OrderCancelledEvent> {

    private static final Logger logger = LoggerFactory.getLogger(OrderCancelledEventHandler.class);
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final PackagedOrdersRepository packagedOrdersRepository;
    private final AssignedOrdersRepository assignedOrdersRepository;

    @Override
    @Transactional
    public void handle(OrderCancelledEvent event) {

        logger.info("Received OrderCancelledEvent for order ID: {}. Reverting stock.", event.getOrderId());

        if (event.getItems() == null || event.getItems().isEmpty()) {
            logger.warn("OrderCancelledEvent for order ID: {} contained no items. No stock to revert.", event.getOrderId());
            return;
        }

        try {
            for (OrderItem item : event.getItems()) {
                if (item.getProductId() == null || item.getQuantity() <= 0) {
                    logger.warn("Invalid item data in OrderCancelledEvent for order ID: {}. ProductId: {}, Quantity: {}. Skipping item.",
                            event.getOrderId(), item.getProductId(), item.getQuantity());
                    continue;
                }

                UUID productId = UUID.fromString(item.getProductId());
                int quantityToRevert = item.getQuantity();

                Stock stock = stockRepository.findById(productId).orElse(null);

                if (stock == null) {
                    logger.warn("Product with ID: {} for order cancellation ID: {} not found in stock. Cannot revert quantity {}. " +
                                    "This might indicate the product was deleted or never existed in this warehouse.",
                            item.getProductId(), event.getOrderId(), quantityToRevert);

                    continue;
                }

                int currentQuantity = stock.getQuantity();
                int newQuantity = currentQuantity + quantityToRevert;

                stockService.updateStock(
                        productId,
                        null,
                        newQuantity,
                        stock.getThreshold(),
                        null,
                        null
                );

                logger.info("Stock reverted for product ID: {}. Order ID: {}. Quantity reverted: {}. Old quantity: {}. New quantity: {}",
                        productId, event.getOrderId(), quantityToRevert, currentQuantity, newQuantity);
            }

            UUID orderId = UUID.fromString(event.getOrderId());
            packagedOrdersRepository.deleteById(orderId);
            assignedOrdersRepository.deleteById(orderId);

            logger.info("Successfully reverted stock for all items in cancelled order ID: {}", event.getOrderId());

        } catch (IllegalArgumentException e) {
            logger.error("Error processing OrderCancelledEvent for order ID: {}. Invalid UUID format for a product ID. Details: {}", event.getOrderId(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Critical error processing OrderCancelledEvent for order ID: {}. Details: {}", event.getOrderId(), e.getMessage(), e);
        }
    }
}