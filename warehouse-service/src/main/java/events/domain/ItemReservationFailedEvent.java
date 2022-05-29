package events.domain;

import com.breader.warehouse.domain.item.OrderId;
import events.DomainEventPayload;

public record ItemReservationFailedEvent(OrderId orderId) implements DomainEventPayload {
}
