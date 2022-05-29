package com.breader.order.application;

import events.domain.ItemReservationFailedEvent;
import events.domain.ItemReservedEvent;
import com.breader.order.domain.order.ItemId;
import com.breader.order.domain.order.Order;
import com.breader.order.domain.order.OrderId;
import events.domain.OrderCreatedEvent;
import events.domain.PlacingOrderFailedEvent;
import events.DomainEventPayload;
import com.breader.order.infrastructure.repository.OptimisticLockingException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderService {

    private final EventEmitter eventEmitter;
    private final OrderRepository orderRepository;

    private final String orderTopic;

    public OrderId createOrder(ItemId itemId) {
        Order order = Order.forItem(itemId);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order.getOrderId(), itemId);

        orderRepository.save(order);
        eventEmitter.emit(orderTopic, orderCreatedEvent);

        return order.getOrderId();
    }

    @Retryable(value = OptimisticLockingException.class)
    public void handleEvent(ItemReservedEvent itemReservedEvent) {
        OrderId orderId = itemReservedEvent.orderId();
        ItemId itemId = itemReservedEvent.itemId();

        Optional<Order> orderOpt = orderRepository.fetch(orderId);
        orderOpt.ifPresentOrElse(
            order -> {
                List<DomainEventPayload> placeEventList = order.place();
                orderRepository.save(order);
                eventEmitter.emit(orderTopic, placeEventList);
            },
            () -> eventEmitter.emit(orderTopic, new PlacingOrderFailedEvent(orderId, itemId))
        );
    }

    @Retryable(value = OptimisticLockingException.class)
    public void handleEvent(ItemReservationFailedEvent itemReservationFailedEvent) {
        OrderId orderId = itemReservationFailedEvent.orderId();

        Optional<Order> orderOpt = orderRepository.fetch(orderId);
        orderOpt.ifPresent(order -> {
            order.cancel();
            orderRepository.save(order);
        });
    }

}
