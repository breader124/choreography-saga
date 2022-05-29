package com.breader.warehouse.domain.item;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import com.breader.warehouse.infrastructure.repository.ItemIdDynamoConverter;
import com.breader.warehouse.infrastructure.repository.OrderIdDynamoConverter;
import events.DomainEventPayload;
import events.domain.ItemReservationFailedEvent;
import events.domain.ItemReservedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "WarehouseDB")
public class Item {

    @DynamoDBHashKey(attributeName = "ItemId")
    @DynamoDBTypeConverted(converter = ItemIdDynamoConverter.class)
    private ItemId itemId;

    @DynamoDBAttribute(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Quantity")
    private Long quantity;

    @DynamoDBAttribute(attributeName = "Orders")
    @DynamoDBTypeConverted(converter = OrderIdDynamoConverter.class)
    private List<OrderId> reservedForOrders;

    @DynamoDBVersionAttribute(attributeName = "Version")
    private Integer version;

    public static Item withNameAndQuantity(String name, Long quantity) {
        Item item = new Item();
        item.setItemId(ItemId.newId());
        item.setName(name);
        item.setQuantity(quantity);
        item.setReservedForOrders(new ArrayList<>());
        return item;
    }

    public List<DomainEventPayload> reserve(OrderId orderId) {
        List<DomainEventPayload> domainEventList = new ArrayList<>();
        if (quantity > 0 && !reservedForOrders.contains(orderId)) {
            quantity--;
            reservedForOrders.add(orderId);
            domainEventList.add(new ItemReservedEvent(orderId, itemId));
        } else {
            domainEventList.add(new ItemReservationFailedEvent(orderId));
        }

        return domainEventList;
    }

    public void unlock(OrderId orderId) {
        quantity++;
        reservedForOrders.remove(orderId);
    }

}
