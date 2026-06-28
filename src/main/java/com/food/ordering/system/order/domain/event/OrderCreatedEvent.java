package com.food.ordering.system.order.domain.event;

import com.food.ordering.system.order.domain.model.valueobject.OrderId;
import com.food.ordering.system.shared.domain.event.DomainEvent;
import com.food.ordering.system.shared.domain.valueobject.CustomerId;

import java.math.BigDecimal;

/**
 * Domain Event raised when a new Order is created.
 */
public class OrderCreatedEvent extends DomainEvent {

    public static final String EVENT_TYPE = "order.created";

    private final OrderId orderId;
    private final CustomerId customerId;
    private final BigDecimal totalAmount;

    public OrderCreatedEvent(OrderId orderId, CustomerId customerId, BigDecimal totalAmount) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    public OrderId getOrderId() { return orderId; }
    public CustomerId getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}

