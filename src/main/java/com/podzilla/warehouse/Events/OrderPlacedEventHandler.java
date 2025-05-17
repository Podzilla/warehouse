package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderPlacedEventHandler implements EventHandler<OrderPlacedEvent> {
    private final PackagedOrdersRepository packagedOrdersRepository;

    @Override
    public void handle(OrderPlacedEvent event) {
        //create packaged order
        PackagedOrders packagedOrder = new PackagedOrders(UUID.fromString(event.getOrderId()));
        packagedOrdersRepository.save(packagedOrder);
        
        //TODO: send inventory updated event
    }
}
