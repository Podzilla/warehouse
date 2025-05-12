package com.podzilla.warehouse.Events;

import com.podzilla.warehouse.Services.StockService;
import com.podzilla.warehouse.Events.*;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    // TODO: Waiting on RabbitMQ Completion to Finish the Listener.

    private final StockService stockService;
    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;

    //@RabbitListener(queues = RabbitMQConfig.ORDER_PLACED_QUEUE)
    public void onOrderPlaced(OrderPlacedEvent event) {
        boolean allAvailable = true;

        // Check & reserve stock
        for (OrderPlacedEvent.OrderItem item : event.getItems()) {
            Stock stock = stockRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Unknown product " + item.getProductId()));
            if (stock.getQuantity() < item.getQuantity()) {
                allAvailable = false;
                int missing = item.getQuantity() - stock.getQuantity();
                // Publish ERP restock event
                ErpRestockEvent erpEvent = EventFactory.createErpRestockEvent(item.getProductId(), missing);
                rabbitTemplate.convertAndSend(
//                        RabbitMQConfig.ERP_EXCHANGE,
//                        RabbitMQConfig.ERP_ROUTING,
                        erpEvent
                );
                break;
            }
        }

        if (allAvailable) {
            // Deduct stock
            event.getItems().forEach(item ->
                    stockService.updateStock(
                            item.getProductId(),
                            null,
                            stockRepository.findById(item.getProductId()).get().getQuantity() - item.getQuantity(),
                            null,
                            null,
                            null

                    )
            );


            // TODO: Emit event to Courier service
//            CourierDispatchEvent courierEvent = new CourierDispatchEvent(event.getOrderId());
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.COURIER_EXCHANGE,
//                    RabbitMQConfig.COURIER_ROUTING,
//                    courierEvent
//            );
        }
    }
}
