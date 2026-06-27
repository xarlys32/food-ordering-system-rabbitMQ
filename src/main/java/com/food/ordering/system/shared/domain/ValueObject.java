package com.food.ordering.system.shared.domain;

import java.util.Objects;

/**
 * Base class for Value Objects.
 * Value Objects are immutable and equality is based on their attributes.
 */
public abstract class ValueObject {

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    protected void validate() {
        // Override to add self-validation logic
    }

    protected static void assertNotNull(Object value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
    }

    protected static void assertNotBlank(String value, String fieldName) {
        assertNotNull(value, fieldName);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }
}

