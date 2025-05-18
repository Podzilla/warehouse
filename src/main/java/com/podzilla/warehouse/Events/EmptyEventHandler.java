package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.BaseEvent;
import org.springframework.stereotype.Component;

@Component
public class EmptyEventHandler implements EventHandler<BaseEvent> {
    @Override
    public void handle(BaseEvent event) {
        // Empty implementation - does nothing
    }
}