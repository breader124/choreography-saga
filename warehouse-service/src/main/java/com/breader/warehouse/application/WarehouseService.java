package com.breader.warehouse.application;

import events.domain.OrderCreatedEvent;
import com.breader.warehouse.domain.item.OrderId;
import events.domain.PlacingOrderFailedEvent;
import com.breader.warehouse.domain.item.Item;
import com.breader.warehouse.domain.item.ItemId;
import events.domain.ItemReservationFailedEvent;
import events.DomainEventPayload;
import com.breader.warehouse.infrastructure.repository.OptimisticLockingException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WarehouseService {

    private final EventEmitter eventEmitter;
    private final WarehouseRepository warehouseRepository;

    private final String itemTopic;

    public ItemId createItem(String name, Long quantity) {
        Item item = Item.withNameAndQuantity(name, quantity);
        return warehouseRepository.save(item);
    }

    @Retryable(value = OptimisticLockingException.class)
    public void handleEvent(OrderCreatedEvent orderCreatedEvent) {
        OrderId orderId = orderCreatedEvent.orderId();
        ItemId itemId = orderCreatedEvent.itemId();

        Optional<Item> itemOpt = warehouseRepository.fetch(itemId);
        itemOpt.ifPresentOrElse(
            item -> {
                List<DomainEventPayload> placementEventList = item.reserve(orderId);
                warehouseRepository.save(item);
                eventEmitter.emit(itemTopic, placementEventList);
            },
            () -> eventEmitter.emit(itemTopic, new ItemReservationFailedEvent(orderId))
        );
    }

    @Retryable(value = OptimisticLockingException.class)
    public void handleEvent(PlacingOrderFailedEvent placingOrderFailedEvent) {
        OrderId orderId = placingOrderFailedEvent.orderId();
        ItemId itemId = placingOrderFailedEvent.itemId();

        Optional<Item> itemOpt = warehouseRepository.fetch(itemId);
        itemOpt.ifPresent(item -> {
            item.unlock(orderId);
            warehouseRepository.save(item);
        });
    }

}
