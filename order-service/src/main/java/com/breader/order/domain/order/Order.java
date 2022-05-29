package com.breader.order.domain.order;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import events.domain.OrderPlacedEvent;
import events.domain.PlacingOrderFailedEvent;
import events.DomainEventPayload;
import com.breader.order.infrastructure.repository.ItemIdDynamoConverter;
import com.breader.order.infrastructure.repository.OrderIdDynamoConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.breader.order.domain.order.OrderStatus.CANCELLED;
import static com.breader.order.domain.order.OrderStatus.PENDING_PLACEMENT;
import static com.breader.order.domain.order.OrderStatus.PLACED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "OrderDB")
public class Order {

    @DynamoDBHashKey(attributeName = "OrderId")
    @DynamoDBTypeConverted(converter = OrderIdDynamoConverter.class)
    private OrderId orderId;

    @DynamoDBAttribute(attributeName = "Status")
    @DynamoDBTypeConvertedEnum
    private OrderStatus status;

    @DynamoDBAttribute(attributeName = "ItemId")
    @DynamoDBTypeConverted(converter = ItemIdDynamoConverter.class)
    private ItemId itemId;

    @DynamoDBVersionAttribute(attributeName = "Version")
    private Integer version;

    public static Order forItem(ItemId itemId) {
        Order order = new Order();
        order.setOrderId(OrderId.newId());
        order.setStatus(PENDING_PLACEMENT);
        order.setItemId(itemId);
        return order;
    }

    public List<DomainEventPayload> place() {
        List<DomainEventPayload> domainEventList = new ArrayList<>();
        if (status == PENDING_PLACEMENT && itemId != null) {
            status = PLACED;
            domainEventList.add(new OrderPlacedEvent(orderId));
        } else {
            domainEventList.add(new PlacingOrderFailedEvent(orderId, itemId));
        }

        return domainEventList;
    }

    public void cancel() {
        status = CANCELLED;
    }

}
