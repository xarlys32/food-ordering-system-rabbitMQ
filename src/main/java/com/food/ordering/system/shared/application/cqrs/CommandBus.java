package com.food.ordering.system.shared.application.cqrs;

/**
 * Command Bus — single entry point to dispatch any {@link Command}.
 *
 * <p>The caller sends a command and gets back the result produced
 * by the corresponding {@link CommandHandler}. The bus is responsible
 * for routing the command to the correct handler.</p>
 */
public interface CommandBus {

    /**
     * Dispatches the given command to its registered handler.
     *
     * @param command the command to execute
     * @param <R>     return type declared by the command
     * @return the result produced by the handler
     */
    <R> R dispatch(Command<R> command);
}

