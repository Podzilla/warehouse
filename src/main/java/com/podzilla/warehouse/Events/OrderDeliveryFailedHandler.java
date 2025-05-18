package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.OrderDeliveryFailedEvent;
import com.podzilla.warehouse.Models.FailedOrders;
import com.podzilla.warehouse.Services.FailedOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderDeliveryFailedHandler implements EventHandler<OrderDeliveryFailedEvent> {

    @Autowired
    private FailedOrdersService failedOrdersService;

    @Override
    public void handle(OrderDeliveryFailedEvent event) {
        UUID orderId = UUID.fromString(event.getOrderId());
        UUID courierId = UUID.fromString(event.getCourierId());

        FailedOrders failedOrder = new FailedOrders(orderId, courierId, event.getReason());
        failedOrdersService.save(failedOrder);
    }
}
