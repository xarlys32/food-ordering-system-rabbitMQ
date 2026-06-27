package com.food.ordering.system.order.domain.model;

import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.exception.OrderDomainException;
import com.food.ordering.system.order.domain.model.valueobject.OrderId;
import com.food.ordering.system.order.domain.model.valueobject.OrderStatus;
import com.food.ordering.system.shared.domain.model.AggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Order Aggregate Root.
 * Encapsulates all business rules related to an Order.
 */
public class Order extends AggregateRoot {

    private final OrderId id;
    private final String customerId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Order(OrderId id, String customerId, BigDecimal totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    private Order(OrderId id, String customerId, BigDecimal totalAmount,
                  OrderStatus status, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.customerId = Objects.requireNonNull(customerId);
        this.totalAmount = Objects.requireNonNull(totalAmount);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    /**
     * Reconstitutes an Order from its persisted state (used by the infrastructure mapper).
     * Does NOT register domain events.
     */
    public static Order reconstitute(OrderId id, String customerId, BigDecimal totalAmount,
                                     OrderStatus status, Instant createdAt, Instant updatedAt) {
        return new Order(id, customerId, totalAmount, status, createdAt, updatedAt);
    }

    /**
     * Factory method: creates a new Order and registers the corresponding domain event.
     */
    public static Order create(String customerId, BigDecimal totalAmount) {
        if (customerId == null || customerId.isBlank()) {
            throw new OrderDomainException("Customer ID must not be blank");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderDomainException("Total amount must be greater than zero");
        }

        Order order = new Order(OrderId.generate(), customerId, totalAmount);
        order.registerEvent(new OrderCreatedEvent(order.id, order.customerId, order.totalAmount));
        return order;
    }

    public void confirm() {
        if (this.status != OrderStatus.PENDING) {
            throw new OrderDomainException("Only PENDING orders can be confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = Instant.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.SHIPPED || this.status == OrderStatus.DELIVERED) {
            throw new OrderDomainException("Cannot cancel an order that is already SHIPPED or DELIVERED");
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = Instant.now();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public OrderId getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}



