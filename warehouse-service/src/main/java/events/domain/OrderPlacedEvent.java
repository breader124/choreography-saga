package events.domain;

import com.breader.warehouse.domain.item.OrderId;
import events.DomainEventPayload;

public record OrderPlacedEvent(OrderId orderId) implements DomainEventPayload {
}
