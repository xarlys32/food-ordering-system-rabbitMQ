package com.food.ordering.system.order.application.dto.response;

import com.food.ordering.system.order.domain.model.valueobject.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO returned from Application Service use cases.
 */
public record OrderResponse(
        UUID id,
        String customerId,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt
) {}

