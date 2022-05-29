package com.breader.order.domain.order;

import java.util.UUID;

public record OrderId(UUID id) {

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }

}
