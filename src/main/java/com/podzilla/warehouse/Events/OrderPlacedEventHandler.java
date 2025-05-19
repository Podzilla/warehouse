package com.podzilla.warehouse.Events;

import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Models.Stock;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import com.podzilla.warehouse.Repositories.StockRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderPlacedEventHandler implements EventHandler<OrderPlacedEvent> {
    private final PackagedOrdersRepository packagedOrdersRepository;
    private final StockRepository stockRepository;

    @Override
    public void handle(OrderPlacedEvent event) {
        // create packaged order
        System.out.println("Received OrderPlacedEvent: " + event);
        List<Stock> stocks = event.getItems().stream()
                .map(item -> stockRepository.findById(UUID.fromString(item.getProductId()))
                        .get())
                .toList();

        PackagedOrders packagedOrder = PackagedOrders.builder()
                .orderId(UUID.fromString(event.getOrderId()))
                .deliveryAddress(event.getDeliveryAddress())
                .items(stocks)
                .totalAmount(event.getTotalAmount())
                .orderLatitude(event.getOrderLatitude())
                .orderLongitude(event.getOrderLongitude())
                .confirmationType(event.getConfirmationType())
                .signature(event.getSignature())
                .build();
        System.out.println("Packaged order created: " + packagedOrder);
        packagedOrdersRepository.save(packagedOrder);
    }
}
