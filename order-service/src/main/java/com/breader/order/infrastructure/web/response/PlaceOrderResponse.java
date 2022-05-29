package com.breader.order.infrastructure.web.response;

import com.breader.order.domain.order.OrderId;

public record PlaceOrderResponse(OrderId orderId) {
}
