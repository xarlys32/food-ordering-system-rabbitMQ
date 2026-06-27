package com.food.ordering.system.order.domain.exception;

import com.food.ordering.system.order.domain.model.valueobject.OrderId;

/**
 * Thrown when an Order cannot be found by its identifier.
 */
public class OrderNotFoundException extends OrderDomainException {

    public OrderNotFoundException(OrderId orderId) {
        super("Order not found with id: " + orderId.getValue());
    }
}

