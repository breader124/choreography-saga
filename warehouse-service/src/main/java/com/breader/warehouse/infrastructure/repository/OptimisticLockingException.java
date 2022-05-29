package com.breader.warehouse.infrastructure.repository;

import com.breader.warehouse.domain.item.ItemId;

public class OptimisticLockingException extends RuntimeException {

    public OptimisticLockingException(ItemId itemId) {
        super("Order with id = %s has been modified".formatted(itemId.asString()));
    }

}
