package com.food.ordering.system.order.infrastructure.messaging.consumer;

import com.food.ordering.system.order.infrastructure.messaging.event.OrderCreatedKafkaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Primary / Driving Adapter — Kafka Consumer.
 * Listens to Kafka topics and translates messages into application use-case calls.
 * Add @KafkaListener methods for each topic/event you need to consume.
 */
@Component
public class OrderEventKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventKafkaConsumer.class);

    /**
     * Example: listen to order.created events produced by another service or by this service itself
     * (e.g. for projections, notifications, sagas, etc.).
     */
    @KafkaListener(
            topics  = "${kafka.topics.order-created:order.created}",
            groupId = "${kafka.consumer.group-id:order-consumer-group}"
    )
    public void onOrderCreated(OrderCreatedKafkaEvent event) {
        log.info("Received OrderCreatedKafkaEvent: orderId={}, customerId={}",
                event.orderId(), event.customerId());

        // TODO: delegate to an inbound use-case / application service if needed
    }
}

