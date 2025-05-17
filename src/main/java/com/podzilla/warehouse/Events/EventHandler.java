package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.BaseEvent;

public interface EventHandler<T extends BaseEvent> {
    void handle(T event);
}
