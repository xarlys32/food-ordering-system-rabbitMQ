package com.food.ordering.system.order.domain.port.out;

import com.food.ordering.system.shared.domain.event.DomainEvent;

/**
 * Outbound Port (Secondary / Driven).
 * Defines the messaging contract for publishing domain events.
 * Implemented by the infrastructure Kafka adapter.
 */
public interface OrderEventPublisherPort {

    void publish(DomainEvent event);
}

