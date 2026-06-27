package com.food.ordering.system.order.infrastructure.api.controller;

import com.food.ordering.system.order.application.command.CreateOrderCommand;
import com.food.ordering.system.order.application.dto.response.OrderResponse;
import com.food.ordering.system.order.application.query.GetOrderQuery;
import com.food.ordering.system.order.infrastructure.api.dto.CreateOrderRequest;
import com.food.ordering.system.order.infrastructure.api.dto.OrderApiResponse;
import com.food.ordering.system.shared.application.cqrs.CommandBus;
import com.food.ordering.system.shared.application.cqrs.QueryBus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Primary / Driving Adapter — REST Controller.
 *
 * <p>Translates HTTP requests into CQRS Commands/Queries and dispatches
 * them through the bus. It depends only on the bus abstractions —
 * never on application services or domain objects directly.</p>
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CommandBus commandBus;
    private final QueryBus   queryBus;

    public OrderController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus   = queryBus;
    }

    @PostMapping
    public ResponseEntity<OrderApiResponse> createOrder(@RequestBody CreateOrderRequest request) {
        var command  = new CreateOrderCommand(request.customerId(), request.totalAmount());
        var response = commandBus.<OrderResponse>dispatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(toApiResponse(response));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderApiResponse> getOrder(@PathVariable UUID orderId) {
        var response = queryBus.<OrderResponse>dispatch(new GetOrderQuery(orderId));
        return ResponseEntity.ok(toApiResponse(response));
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private OrderApiResponse toApiResponse(OrderResponse response) {
        return new OrderApiResponse(
                response.id(),
                response.customerId(),
                response.totalAmount(),
                response.status(),
                response.createdAt()
        );
    }
}

