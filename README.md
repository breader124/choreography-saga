## Purpose
Willing to try out how does the process of choreography saga implementation looks alike.

## Architecture view

### order-service
Service responsible for managing orders. Using it it's possible to create an order for one item. To successfully create order, items needs to be available in the warehouse, but as this service is not responsible for items management, warehouse-service must be involved in the process. After creation of an order and while waiting for an event emitted by warehouse-service, order has PENDING_PLACEMENT status. It's an implementation of semantic lock coutermeasure. Based on type of the event received from warehouse, order can become CANCELLED or PLACED.

### warehouse-service
Service responsible for managing items. It participates in saga initiated by order-service. After receiving event about order creation, this service checks quantity of an item available in the magazine and if it's present there, reserves it. Corresponding events are emitted in case of proper reservation as well as unavailable item. Additionally, fact of an reservation might be reverted is it turns out that eventually order cannot be placed, what is communicated by order-service by sending proper events to the Kafka topic.

## In scope
- Implementation of choreography based saga
- Utilisation of Kafka to establish communication between services
- Utilisation DynamoDB as a storage for orders and items

## Out of scope
- Implementation of transactional outbox pattern
