package events;

import events.domain.ItemReservationFailedEvent;
import events.domain.ItemReservedEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
    @Type(name = "ItemReservedEvent", value = ItemReservedEvent.class),
    @Type(name = "ItemReservationFailedEvent", value = ItemReservationFailedEvent.class)
})
public interface DomainEventPayload {
}
