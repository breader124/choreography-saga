package com.breader.order.infrastructure.web;

import com.breader.order.application.OrderService;
import com.breader.order.domain.order.ItemId;
import com.breader.order.domain.order.OrderId;
import com.breader.order.infrastructure.web.request.PlaceOrderRequest;
import com.breader.order.infrastructure.web.response.PlaceOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("orders")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest request) {
        ItemId requestedItemId = ItemId.fromId(request.itemId());
        OrderId orderId = orderService.createOrder(requestedItemId);

        PlaceOrderResponse response = new PlaceOrderResponse(orderId);
        return ResponseEntity.status(ACCEPTED).body(response);
    }

}
