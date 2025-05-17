package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.BaseEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.mq.events.OrderStockReservationRequestedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventHandlerFactory {
    private final HashMap<Class<? extends BaseEvent>, EventHandler<? extends BaseEvent>> handlers;

    @Autowired
    public EventHandlerFactory(OrderPlacedEventHandler OrderPlacedEventHandler,OrderStockReservationEventHandler orderStockReservationEventHandler) {
        handlers = new HashMap<>();
        handlers.put(OrderPlacedEvent.class, OrderPlacedEventHandler);
        handlers.put(OrderStockReservationRequestedEvent.class, orderStockReservationEventHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEvent> EventHandler<T> getHandler(T event) {
        EventHandler<? extends BaseEvent> handler = handlers.get(event.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for event type: " + event.getClass().getName());
        }
        return (EventHandler<T>) handler;
    }
}
