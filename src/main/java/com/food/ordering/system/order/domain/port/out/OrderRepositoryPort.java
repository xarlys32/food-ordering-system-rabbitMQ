package com.food.ordering.system.order.domain.port.out;

import com.food.ordering.system.order.domain.model.Order;
import com.food.ordering.system.order.domain.model.valueobject.OrderId;

import java.util.Optional;

/**
 * Outbound Port (Secondary / Driven).
 * Defines the persistence contract for Orders.
 * Implemented by the infrastructure persistence adapter (PostgreSQL).
 */
public interface OrderRepositoryPort {

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

    void deleteById(OrderId orderId);
}

