package com.food.ordering.system.shared.application.cqrs;

/**
 * Marker interface for all Commands in the CQRS pattern.
 *
 * @param <R> the return type produced by the command handler.
 *            Use {@link Void} for fire-and-forget commands.
 */
public interface Command<R> {
}

