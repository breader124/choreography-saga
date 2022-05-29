package com.breader.order.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.breader.order.application.OrderRepository;
import com.breader.order.domain.order.Order;
import com.breader.order.domain.order.OrderId;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DynamoDbOrderRepository implements OrderRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Override
    public void save(Order order) {
        try {
            dynamoDBMapper.save(order);
        } catch (ConditionalCheckFailedException optimisticLockExc) {
            throw new OptimisticLockingException(order.getOrderId());
        }
    }

    @Override
    public Optional<Order> fetch(OrderId orderId) {
        return Optional.ofNullable(dynamoDBMapper.load(Order.class, orderId));
    }

}
