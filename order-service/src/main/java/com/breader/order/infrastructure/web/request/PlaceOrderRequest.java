package com.breader.order.infrastructure.web.request;

import java.util.UUID;

public record PlaceOrderRequest(UUID itemId) {
}
