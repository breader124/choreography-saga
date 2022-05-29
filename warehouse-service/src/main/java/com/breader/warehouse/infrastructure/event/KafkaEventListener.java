package com.breader.warehouse.infrastructure.event;

import com.breader.warehouse.application.WarehouseService;
import events.domain.OrderCreatedEvent;
import events.domain.PlacingOrderFailedEvent;
import events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
public class KafkaEventListener {

    private final WarehouseService warehouseService;

    @KafkaListener(topics = "${topic.order}")
    public void tokenTopicKafkaListener(@Payload DomainEvent domainEvent) {
        if (domainEvent.payload() instanceof OrderCreatedEvent orderCreatedEvent) {
            warehouseService.handleEvent(orderCreatedEvent);
        }

        if (domainEvent.payload() instanceof PlacingOrderFailedEvent placingOrderFailedEvent) {
            warehouseService.handleEvent(placingOrderFailedEvent);
        }
    }

}
