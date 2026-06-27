package com.food.ordering.system.order.application.query;

import com.food.ordering.system.order.application.dto.response.OrderResponse;
import com.food.ordering.system.shared.application.cqrs.Query;

import java.util.UUID;

/**
 * Query to retrieve an existing Order by its identifier.
 * Produces no side effects.
 */
public record GetOrderQuery(
        UUID orderId
) implements Query<OrderResponse> {
}

