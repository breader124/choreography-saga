package com.breader.warehouse.application;

import events.DomainEventPayload;

import java.util.List;

public interface EventEmitter {

    void emit(String topic, DomainEventPayload event);

    default void emit(String topic, List<DomainEventPayload> eventPayloadList) {
        eventPayloadList.forEach(event -> emit(topic, event));
    }

}
