package com.food.ordering.system.order.application.command;

import com.food.ordering.system.order.application.dto.response.OrderResponse;
import com.food.ordering.system.shared.application.cqrs.Command;

import java.math.BigDecimal;

/**
 * Command to create a new Order.
 * Carries the intent and the data needed to fulfill it.
 */
public record CreateOrderCommand(
        String customerId,
        BigDecimal totalAmount
) implements Command<OrderResponse> {
}

