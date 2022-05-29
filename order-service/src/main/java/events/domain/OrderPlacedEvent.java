package events.domain;

import com.breader.order.domain.order.OrderId;
import events.DomainEventPayload;

public record OrderPlacedEvent(OrderId orderId) implements DomainEventPayload {
}
