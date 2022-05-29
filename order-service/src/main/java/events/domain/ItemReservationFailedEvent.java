package events.domain;

import com.breader.order.domain.order.OrderId;
import events.DomainEventPayload;

public record ItemReservationFailedEvent(OrderId orderId) implements DomainEventPayload {
}
