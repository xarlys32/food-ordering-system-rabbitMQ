package com.food.ordering.system.order.infrastructure.persistence.mapper;

import com.food.ordering.system.order.domain.model.Order;
import com.food.ordering.system.order.domain.model.valueobject.OrderId;
import com.food.ordering.system.order.infrastructure.persistence.entity.OrderJpaEntity;

/**
 * Maps between the Order domain model and the JPA entity.
 * Keeps the domain model free from any JPA/persistence concerns.
 */
public class OrderPersistenceMapper {

    private OrderPersistenceMapper() {}

    public static OrderJpaEntity toEntity(Order order) {
        return new OrderJpaEntity(
                order.getId().getValue(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public static Order toDomain(OrderJpaEntity entity) {
        // Reconstruct via reflection-friendly factory — extend as needed
        return Order.reconstitute(
                OrderId.of(entity.getId()),
                entity.getCustomerId(),
                entity.getTotalAmount(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

