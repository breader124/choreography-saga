package events.domain;

import com.breader.warehouse.domain.item.OrderId;
import com.breader.warehouse.domain.item.ItemId;
import events.DomainEventPayload;

public record PlacingOrderFailedEvent(OrderId orderId, ItemId itemId) implements DomainEventPayload {
}
