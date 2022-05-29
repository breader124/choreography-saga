package com.breader.order.infrastructure.event;

import com.breader.order.application.OrderService;
import events.domain.ItemReservationFailedEvent;
import events.domain.ItemReservedEvent;
import events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@RequiredArgsConstructor
public class KafkaEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "${topic.item}")
    public void itemTopicKafkaListener(@Payload DomainEvent domainEvent) {
        if (domainEvent.payload() instanceof ItemReservationFailedEvent itemReservationFailedEvent) {
            orderService.handleEvent(itemReservationFailedEvent);
        }

        if (domainEvent.payload() instanceof ItemReservedEvent itemReservedEvent) {
            orderService.handleEvent(itemReservedEvent);
        }
    }

}
