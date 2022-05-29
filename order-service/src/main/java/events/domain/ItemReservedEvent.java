package events.domain;

import com.breader.order.domain.order.ItemId;
import com.breader.order.domain.order.OrderId;
import events.DomainEventPayload;

public record ItemReservedEvent(OrderId orderId, ItemId itemId) implements DomainEventPayload {
}
