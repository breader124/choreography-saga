package com.breader.warehouse.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.breader.warehouse.domain.item.OrderId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderIdDynamoConverter implements DynamoDBTypeConverter<List<String>, List<OrderId>> {

    @Override
    public List<String> convert(List<OrderId> orderIds) {
        return orderIds.stream()
            .map(orderId -> orderId.id().toString())
            .collect(Collectors.toList()); // NOSONAR
    }

    @Override
    public List<OrderId> unconvert(List<String> orderIds) {
        return orderIds.stream()
            .map(orderId -> new OrderId(UUID.fromString(orderId)))
            .collect(Collectors.toList()); // NOSONAR
    }

}
