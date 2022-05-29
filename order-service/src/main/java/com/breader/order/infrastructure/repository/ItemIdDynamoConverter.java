package com.breader.order.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.breader.order.domain.order.ItemId;
import com.breader.order.domain.order.OrderId;

import java.util.UUID;

public class ItemIdDynamoConverter implements DynamoDBTypeConverter<String, ItemId> {

    @Override
    public String convert(ItemId itemId) {
        return itemId.id().toString();
    }

    @Override
    public ItemId unconvert(String orderId) {
        return new ItemId(UUID.fromString(orderId));
    }

}
