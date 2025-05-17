package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.OrderDeliveryFailedEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderDeliveryFailedHandler implements EventHandler<OrderDeliveryFailedEvent> {

    @Override
    public void handle(OrderDeliveryFailedEvent event) {
        //TODO save this to db, add service and repository with crud operation and either reassign or publish fulfillment failed        
    }
}
