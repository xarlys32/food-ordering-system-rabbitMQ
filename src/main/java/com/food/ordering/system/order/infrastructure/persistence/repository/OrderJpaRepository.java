package com.food.ordering.system.order.infrastructure.persistence.repository;

import com.food.ordering.system.order.infrastructure.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA Repository for OrderJpaEntity (PostgreSQL).
 * This is a secondary/driven adapter detail — only the persistence adapter uses it.
 */
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
}

