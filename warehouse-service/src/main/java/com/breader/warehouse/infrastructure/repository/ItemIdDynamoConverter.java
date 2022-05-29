package com.breader.warehouse.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.breader.warehouse.domain.item.ItemId;

import java.util.UUID;

public class ItemIdDynamoConverter implements DynamoDBTypeConverter<String, ItemId> {

    @Override
    public String convert(ItemId itemId) {
        return itemId.id().toString();
    }

    @Override
    public ItemId unconvert(String itemId) {
        return new ItemId(UUID.fromString(itemId));
    }

}
