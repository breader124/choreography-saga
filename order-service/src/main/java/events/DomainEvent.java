package events;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record DomainEvent(UUID eventId, Instant timestamp, DomainEventPayload payload) {
}
