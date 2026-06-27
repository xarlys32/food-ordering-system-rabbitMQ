package com.food.ordering.system.shared.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Marker interface / base class for all Domain Events.
 * A Domain Event represents something meaningful that happened in the domain.
 */
public abstract class DomainEvent {

    private final String eventId;
    private final Instant occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public abstract String getEventType();
}

