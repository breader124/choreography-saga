package com.breader.order.application;

import com.breader.order.domain.order.Order;
import com.breader.order.domain.order.OrderId;

import java.util.Optional;

public interface OrderRepository {

    void save(Order o);

    Optional<Order> fetch(OrderId id);

}
