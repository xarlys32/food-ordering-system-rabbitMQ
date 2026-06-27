package com.food.ordering.system.order.infrastructure.api.dto;

import java.math.BigDecimal;

/**
 * Inbound REST request DTO for creating an Order.
 * Belongs exclusively to the infrastructure/API layer.
 */
public record CreateOrderRequest(
        String customerId,
        BigDecimal totalAmount
) {}

