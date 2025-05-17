package com.podzilla.warehouse.Events;

import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMqEventListener {
    private final EventHandlerFactory eventHandlerFactory;

    @RabbitListener(queues = EventsConstants.WAREHOUSE_ORDER_EVENT_QUEUE)
    public void handleWarehouseOrderCreatedEvent(BaseEvent event) {
        EventHandler<BaseEvent> handler = eventHandlerFactory.getHandler(event);
        handler.handle(event);
    }

    @RabbitListener(queues = EventsConstants.WAREHOUSE_INVENTORY_EVENT_QUEUE)
    public void handleWarehouseInventoryEvent(BaseEvent event) {
        EventHandler<BaseEvent> handler = eventHandlerFactory.getHandler(event);
        handler.handle(event);
    }
}
