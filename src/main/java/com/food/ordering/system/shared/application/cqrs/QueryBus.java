package com.food.ordering.system.shared.application.cqrs;

/**
 * Query Bus — single entry point to dispatch any {@link Query}.
 *
 * <p>The caller sends a query and gets back a read-only result.
 * Query dispatching must never trigger state changes.</p>
 */
public interface QueryBus {

    /**
     * Dispatches the given query to its registered handler.
     *
     * @param query the query to execute
     * @param <R>   return type declared by the query
     * @return the result produced by the handler
     */
    <R> R dispatch(Query<R> query);
}

