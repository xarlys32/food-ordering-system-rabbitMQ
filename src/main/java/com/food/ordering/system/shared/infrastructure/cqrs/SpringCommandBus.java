package com.food.ordering.system.shared.infrastructure.cqrs;

import com.food.ordering.system.shared.application.cqrs.Command;
import com.food.ordering.system.shared.application.cqrs.CommandBus;
import com.food.ordering.system.shared.application.cqrs.CommandHandler;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring-managed implementation of {@link CommandBus}.
 *
 * <p>On startup it collects all {@link CommandHandler} beans registered in the
 * Spring context and builds a {@code Class → Handler} map using
 * {@link ResolvableType} to resolve the generic command type at runtime.</p>
 */
@Component
public class SpringCommandBus implements CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public SpringCommandBus(List<CommandHandler<?, ?>> commandHandlers) {
        commandHandlers.forEach(handler -> {
            ResolvableType resolvableType = ResolvableType
                    .forClass(handler.getClass())
                    .as(CommandHandler.class);

            Class<?> commandType = resolvableType.getGeneric(0).resolve();

            if (commandType != null) {
                handlers.put(commandType, handler);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Command<R> command) {
        CommandHandler<Command<R>, R> handler =
                (CommandHandler<Command<R>, R>) handlers.get(command.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No CommandHandler registered for: " + command.getClass().getSimpleName());
        }

        return handler.handle(command);
    }
}

