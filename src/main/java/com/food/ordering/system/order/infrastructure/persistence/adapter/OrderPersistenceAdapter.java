package com.food.ordering.system.order.infrastructure.persistence.adapter;

import com.food.ordering.system.order.domain.model.Order;
import com.food.ordering.system.order.domain.model.valueobject.OrderId;
import com.food.ordering.system.order.domain.port.out.OrderRepositoryPort;
import com.food.ordering.system.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import com.food.ordering.system.order.infrastructure.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Secondary / Driven Adapter — PostgreSQL.
 * Implements the outbound port {@link OrderRepositoryPort} using Spring Data JPA.
 * The domain layer has zero knowledge of this class.
 */
@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;

    public OrderPersistenceAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        var entity = OrderPersistenceMapper.toEntity(order);
        var saved  = jpaRepository.save(entity);
        return OrderPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return jpaRepository.findById(orderId.getValue())
                .map(OrderPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(OrderId orderId) {
        jpaRepository.deleteById(orderId.getValue());
    }
}

