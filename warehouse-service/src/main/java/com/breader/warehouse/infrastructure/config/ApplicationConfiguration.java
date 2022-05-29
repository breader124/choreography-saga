package com.breader.warehouse.infrastructure.config;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.breader.warehouse.application.EventEmitter;
import com.breader.warehouse.application.WarehouseRepository;
import com.breader.warehouse.application.WarehouseService;
import events.DomainEvent;
import com.breader.warehouse.infrastructure.event.KafkaEventEmitter;
import com.breader.warehouse.infrastructure.event.KafkaEventListener;
import com.breader.warehouse.infrastructure.repository.DynamoDbWarehouseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Clock;

@Configuration
public class ApplicationConfiguration {

    @Value("${topic.item}")
    private String itemKafkaTopic;

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    EventEmitter eventEmitter(KafkaTemplate<String, DomainEvent> kafkaTemplate, Clock clock) {
        return new KafkaEventEmitter(kafkaTemplate, clock);
    }

    @Bean
    WarehouseRepository warehouseRepository(DynamoDBMapper dynamoDBMapper) {
        return new DynamoDbWarehouseRepository(dynamoDBMapper);
    }

    @Bean
    WarehouseService warehouseService(EventEmitter eventEmitter, WarehouseRepository warehouseRepository) {
        return new WarehouseService(eventEmitter, warehouseRepository, itemKafkaTopic);
    }

    @Bean
    KafkaEventListener kafkaEventListener(WarehouseService warehouseService) {
        return new KafkaEventListener(warehouseService);
    }

}
