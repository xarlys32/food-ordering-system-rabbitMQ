package com.food.ordering.system.order.application.query.handler;

import com.food.ordering.system.order.application.dto.response.OrderResponse;
import com.food.ordering.system.order.application.mapper.OrderMapper;
import com.food.ordering.system.order.application.query.GetOrderQuery;
import com.food.ordering.system.order.domain.exception.OrderNotFoundException;
import com.food.ordering.system.order.domain.model.valueobject.OrderId;
import com.food.ordering.system.order.domain.port.out.OrderRepositoryPort;
import com.food.ordering.system.shared.application.cqrs.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles {@link GetOrderQuery}.
 * Read-only: never modifies state.
 */
@Component
@Transactional(readOnly = true)
public class GetOrderQueryHandler
        implements QueryHandler<GetOrderQuery, OrderResponse> {

    private final OrderRepositoryPort orderRepository;

    public GetOrderQueryHandler(OrderRepositoryPort orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse handle(GetOrderQuery query) {
        OrderId id = OrderId.of(query.orderId());
        return orderRepository.findById(id)
                .map(OrderMapper::toResponse)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}

