package com.food.ordering.system.shared.application.cqrs;

/**
 * Contract for all Command Handlers.
 *
 * <p>Each handler is responsible for executing exactly one command type
 * and returning the result {@code R}.</p>
 *
 * @param <C> the command type this handler processes
 * @param <R> the result type returned after handling the command
 */
public interface CommandHandler<C extends Command<R>, R> {

    R handle(C command);
}

