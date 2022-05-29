package com.breader.order.infrastructure.event;

import com.breader.order.application.EventEmitter;
import events.DomainEvent;
import events.DomainEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class KafkaEventEmitter implements EventEmitter {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;
    private final Clock clock;

    @Override
    public void emit(String topic, DomainEventPayload event) {
        DomainEvent domainEvent = DomainEvent.builder()
            .eventId(UUID.randomUUID())
            .timestamp(Instant.now(clock))
            .payload(event)
            .build();
        kafkaTemplate.send(topic, domainEvent);
    }

}
