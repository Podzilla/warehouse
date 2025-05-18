package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.BaseEvent;
import com.podzilla.mq.events.OrderCancelledEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.mq.events.OrderStockReservationRequestedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventHandlerFactory {
    private final HashMap<Class<? extends BaseEvent>, EventHandler<? extends BaseEvent>> handlers;
    private final EmptyEventHandler emptyEventHandler;

    @Autowired
    public EventHandlerFactory(OrderPlacedEventHandler OrderPlacedEventHandler,
                               OrderStockReservationEventHandler orderStockReservationEventHandler,
                               OrderCancelledEventHandler orderCancelledEventHandler,
                               EmptyEventHandler emptyEventHandler) {
        this.emptyEventHandler = emptyEventHandler;
        handlers = new HashMap<>();
        handlers.put(OrderPlacedEvent.class, OrderPlacedEventHandler);
        handlers.put(OrderStockReservationRequestedEvent.class, orderStockReservationEventHandler);
        handlers.put(OrderCancelledEvent.class, orderCancelledEventHandler);
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEvent> EventHandler<T> getHandler(T event) {
        EventHandler<? extends BaseEvent> handler = handlers.get(event.getClass());
        if (handler == null) {
            return (EventHandler<T>) emptyEventHandler;
        }
        return (EventHandler<T>) handler;
    }
}
