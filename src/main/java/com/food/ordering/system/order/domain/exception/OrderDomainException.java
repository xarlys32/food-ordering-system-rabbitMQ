package com.food.ordering.system.order.domain.exception;

/**
 * Base domain exception for the Order bounded context.
 */
public class OrderDomainException extends RuntimeException {

    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

