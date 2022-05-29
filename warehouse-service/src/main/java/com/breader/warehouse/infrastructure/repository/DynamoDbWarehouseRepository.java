package com.breader.warehouse.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.breader.warehouse.application.WarehouseRepository;
import com.breader.warehouse.domain.item.Item;
import com.breader.warehouse.domain.item.ItemId;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DynamoDbWarehouseRepository implements WarehouseRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public ItemId save(Item item) {
        try {
            dynamoDBMapper.save(item);
            return item.getItemId();
        } catch (ConditionalCheckFailedException optimisticLockExc) {
            throw new OptimisticLockingException(item.getItemId());
        }
    }

    @Override
    public Optional<Item> fetch(ItemId itemId) {
        return Optional.ofNullable(dynamoDBMapper.load(Item.class, itemId));
    }

}
