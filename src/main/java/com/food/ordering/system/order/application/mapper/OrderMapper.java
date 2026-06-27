package com.food.ordering.system.order.application.mapper;

import com.food.ordering.system.order.application.dto.response.OrderResponse;
import com.food.ordering.system.order.domain.model.Order;

/**
 * Maps between Order domain model and Application layer DTOs.
 */
public class OrderMapper {

    private OrderMapper() {}

    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId().getValue(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}

