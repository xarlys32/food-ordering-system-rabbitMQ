package com.food.ordering.system.customer.contracts;

import com.food.ordering.system.shared.domain.valueobject.CustomerId;

public interface CustomerQueryService {
    boolean isValidUserId(CustomerId customerId);
}
