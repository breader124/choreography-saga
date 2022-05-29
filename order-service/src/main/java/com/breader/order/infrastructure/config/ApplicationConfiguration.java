package com.breader.order.infrastructure.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.breader.order.application.EventEmitter;
import com.breader.order.application.OrderRepository;
import com.breader.order.application.OrderService;
import events.DomainEvent;
import com.breader.order.infrastructure.event.KafkaEventEmitter;
import com.breader.order.infrastructure.event.KafkaEventListener;
import com.breader.order.infrastructure.repository.DynamoDbOrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.EnableRetry;

import java.time.Clock;

@Configuration
@EnableRetry
public class ApplicationConfiguration {

    @Value("${topic.order}")
    private String orderKafkaTopic;

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    EventEmitter eventEmitter(KafkaTemplate<String, DomainEvent> kafkaTemplate, Clock clock) {
        return new KafkaEventEmitter(kafkaTemplate, clock);
    }

    @Bean
    OrderRepository orderRepository(DynamoDBMapper dynamoDBMapper) {
        return new DynamoDbOrderRepository(dynamoDBMapper);
    }

    @Bean
    OrderService orderService(EventEmitter eventEmitter, OrderRepository orderRepository) {
        return new OrderService(eventEmitter, orderRepository, orderKafkaTopic);
    }

    @Bean
    KafkaEventListener kafkaEventListener(OrderService orderService) {
        return new KafkaEventListener(orderService);
    }

}
