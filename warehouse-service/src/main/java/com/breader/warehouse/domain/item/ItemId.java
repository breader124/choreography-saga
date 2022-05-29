package com.breader.warehouse.domain.item;

import java.util.UUID;

public record ItemId(UUID id) {

    public static ItemId newId() {
        return new ItemId(UUID.randomUUID());
    }

    public String asString() {
        return id.toString();
    }

}
