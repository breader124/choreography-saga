package com.breader.order.domain.order;

import java.util.UUID;

public record ItemId(UUID id) {

    public static ItemId fromId(UUID id) {
        return new ItemId(id);
    }

}
