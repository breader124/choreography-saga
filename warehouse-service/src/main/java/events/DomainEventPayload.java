package events;

import events.domain.OrderCreatedEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import events.domain.OrderPlacedEvent;
import events.domain.PlacingOrderFailedEvent;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @Type(name = "OrderCreatedEvent", value = OrderCreatedEvent.class),
    @Type(name = "OrderPlacedEvent", value = OrderPlacedEvent.class),
    @Type(name = "PlacingOrderFailedEvent", value = PlacingOrderFailedEvent.class)
})
public interface DomainEventPayload {
}
