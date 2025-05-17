package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.CourierRegisteredEvent;
import org.springframework.stereotype.Component;

@Component
public class CourierRegisteredEventHandler implements EventHandler<CourierRegisteredEvent> {

    @Override
    public void handle(CourierRegisteredEvent event) {
        System.out.println("Handling Courier Registered Event: " + event);
    }
}
