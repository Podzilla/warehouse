package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.BaseEvent;
import com.podzilla.mq.events.CourierRegisteredEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventHandlerFactory {
    private final HashMap<Class<? extends BaseEvent>, EventHandler<? extends BaseEvent>> handlers;

    public EventHandlerFactory() {
        handlers = new HashMap<>();
        handlers.put(CourierRegisteredEvent.class, new CourierRegisteredEventHandler());
        handlers.put(OrderPlacedEvent.class, new OrderPlacedEventHandler());
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
