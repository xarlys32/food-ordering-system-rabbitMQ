package com.food.ordering.system.shared.infrastructure.cqrs;

import com.food.ordering.system.shared.application.cqrs.Query;
import com.food.ordering.system.shared.application.cqrs.QueryBus;
import com.food.ordering.system.shared.application.cqrs.QueryHandler;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring-managed implementation of {@link QueryBus}.
 *
 * <p>On startup it collects all {@link QueryHandler} beans registered in the
 * Spring context and builds a {@code Class → Handler} map using
 * {@link ResolvableType} to resolve the generic query type at runtime.</p>
 */
@Component
public class SpringQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new HashMap<>();

    public SpringQueryBus(List<QueryHandler<?, ?>> queryHandlers) {
        queryHandlers.forEach(handler -> {
            ResolvableType resolvableType = ResolvableType
                    .forClass(handler.getClass())
                    .as(QueryHandler.class);

            Class<?> queryType = resolvableType.getGeneric(0).resolve();

            if (queryType != null) {
                handlers.put(queryType, handler);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Query<R> query) {
        QueryHandler<Query<R>, R> handler =
                (QueryHandler<Query<R>, R>) handlers.get(query.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No QueryHandler registered for: " + query.getClass().getSimpleName());
        }

        return handler.handle(query);
    }
}

