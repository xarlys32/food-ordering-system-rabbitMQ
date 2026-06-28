package com.food.ordering.system.restaurant.contracts;

import com.food.ordering.system.shared.domain.valueobject.CustomerId;

public interface RestaurantQueryService {
    boolean isValidUserId(CustomerId customerId);
}
