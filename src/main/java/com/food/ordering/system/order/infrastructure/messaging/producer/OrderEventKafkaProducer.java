package com.food.ordering.system.order.infrastructure.messaging.producer;

import com.food.ordering.system.order.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.domain.port.out.OrderEventPublisherPort;
import com.food.ordering.system.order.infrastructure.messaging.event.OrderCreatedKafkaEvent;
import com.food.ordering.system.shared.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Secondary / Driven Adapter — Kafka Producer.
 * Implements the outbound port {@link OrderEventPublisherPort}.
 * Translates domain events into Kafka messages and publishes them.
 */
@Component
public class OrderEventKafkaProducer implements OrderEventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(OrderEventKafkaProducer.class);

    @Value("${kafka.topics.order-created:order.created}")
    private String orderCreatedTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        if (event instanceof OrderCreatedEvent orderCreated) {
            var kafkaEvent = new OrderCreatedKafkaEvent(
                    orderCreated.getEventId(),
                    orderCreated.getEventType(),
                    orderCreated.getOccurredOn(),
                    orderCreated.getOrderId().getValue(),
                    orderCreated.getCustomerId(),
                    orderCreated.getTotalAmount()
            );

            kafkaTemplate.send(orderCreatedTopic, kafkaEvent.orderId().toString(), kafkaEvent)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to publish event [{}] to topic [{}]",
                                    event.getEventType(), orderCreatedTopic, ex);
                        } else {
                            log.info("Event [{}] published to topic [{}] partition [{}] offset [{}]",
                                    event.getEventType(), orderCreatedTopic,
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        } else {
            log.warn("No Kafka publisher registered for event type: {}", event.getEventType());
        }
    }
}

