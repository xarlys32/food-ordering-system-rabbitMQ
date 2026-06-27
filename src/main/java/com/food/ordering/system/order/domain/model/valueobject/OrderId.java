package com.food.ordering.system.order.domain.model.valueobject;

import com.food.ordering.system.shared.domain.valueobject.ValueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing the unique identifier of an Order.
 */
public class OrderId extends ValueObject {

    private final UUID value;

    private OrderId(UUID value) {
        assertNotNull(value, "OrderId");
        this.value = value;
    }

    public static OrderId of(UUID value) {
        return new OrderId(value);
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(String value) {
        return new OrderId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderId orderId)) return false;
        return Objects.equals(value, orderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

