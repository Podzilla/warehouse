package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.OrderPlacedEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedEventHandler implements EventHandler<OrderPlacedEvent> {

    @Override
    public void handle(OrderPlacedEvent event) {
        System.out.println("Handling Order Placed Event: " + event);
    }
}
