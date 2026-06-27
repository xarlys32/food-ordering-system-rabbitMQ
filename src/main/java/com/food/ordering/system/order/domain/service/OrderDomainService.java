package com.food.ordering.system.order.domain.service;

import com.food.ordering.system.order.domain.model.Order;
import com.food.ordering.system.order.domain.model.valueobject.OrderStatus;

/**
 * Domain Service for Order-related business logic that does not naturally
 * belong to a single Aggregate Root.
 */
public class OrderDomainService {

    /**
     * Determines whether an order is eligible for cancellation based on business rules.
     */
    public boolean isCancellable(Order order) {
        return order.getStatus() == OrderStatus.PENDING
                || order.getStatus() == OrderStatus.CONFIRMED;
    }
}

