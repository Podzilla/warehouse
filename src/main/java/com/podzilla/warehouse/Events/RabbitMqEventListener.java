package com.podzilla.warehouse.Events;

import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.BaseEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqEventListener {
    private final EventHandlerFactory eventHandlerFactory;

    public RabbitMqEventListener() {
        this.eventHandlerFactory = new EventHandlerFactory();
    }
    
    @RabbitListener(queues = EventsConstants.ANALYTICS_USER_EVENT_QUEUE)
    public void handleEvent(BaseEvent event) {
        EventHandler<BaseEvent> handler = eventHandlerFactory.getHandler(event);
        handler.handle(event);
    }
}