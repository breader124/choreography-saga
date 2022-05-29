package com.breader.order.infrastructure.repository;

import com.breader.order.domain.order.OrderId;

public class OptimisticLockingException extends RuntimeException {

    public OptimisticLockingException(OrderId orderId) {
        super("Order with id = %s has been modified".formatted(orderId.asString()));
    }

}
