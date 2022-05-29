package com.breader.order.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.breader.order.domain.order.OrderId;

import java.util.UUID;

public class OrderIdDynamoConverter implements DynamoDBTypeConverter<String, OrderId> {

    @Override
    public String convert(OrderId orderId) {
        return orderId.id().toString();
    }

    @Override
    public OrderId unconvert(String orderId) {
        return new OrderId(UUID.fromString(orderId));
    }

}
