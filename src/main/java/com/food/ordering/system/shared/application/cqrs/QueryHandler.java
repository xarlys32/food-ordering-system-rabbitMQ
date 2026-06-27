package com.food.ordering.system.shared.application.cqrs;

/**
 * Contract for all Query Handlers.
 *
 * <p>Each handler is responsible for answering exactly one query type
 * and must never produce side effects.</p>
 *
 * @param <Q> the query type this handler processes
 * @param <R> the result type returned by the handler
 */
public interface QueryHandler<Q extends Query<R>, R> {

    R handle(Q query);
}

