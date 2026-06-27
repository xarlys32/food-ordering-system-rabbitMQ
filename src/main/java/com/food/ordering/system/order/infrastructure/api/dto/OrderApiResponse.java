package com.food.ordering.system.order.infrastructure.api.dto;

import com.food.ordering.system.order.domain.model.valueobject.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Outbound REST response DTO for Order resources.
 * Belongs exclusively to the infrastructure/API layer.
 */
public record OrderApiResponse(
        UUID id,
        String customerId,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt
) {}

