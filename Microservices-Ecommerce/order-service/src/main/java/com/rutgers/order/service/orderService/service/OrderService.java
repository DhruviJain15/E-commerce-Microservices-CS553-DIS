package com.rutgers.order.service.orderService.service;

import com.rutgers.order.service.orderService.OrderServiceApplication;
import com.rutgers.order.service.orderService.dto.InventoryResponse;
import com.rutgers.order.service.orderService.dto.OrderLineItemsDto;
import com.rutgers.order.service.orderService.dto.OrderRequest;
import com.rutgers.order.service.orderService.event.OrderPlacedEvent;
import com.rutgers.order.service.orderService.exceptions.ResourceNotFoundException;
import com.rutgers.order.service.orderService.model.Order;
import com.rutgers.order.service.orderService.model.OrderLineItems;
import com.rutgers.order.service.orderService.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final ObservationRegistry observationRegistry;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    public Order placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLinesItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLinesItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems ::getSkuCode).toList();
        List<Integer> quantity = order.getOrderLineItemsList().stream().map(OrderLineItems ::getQuantity).toList();

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        return inventoryServiceObservation.observe(() -> {
            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder ->  uriBuilder.queryParam("skuCode", skuCodes).queryParam("quantity", quantity).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
            //TODO: What if inventoryResponses is an empty list which happens when the product is not mentioned in the inventory. Works good for product present in inventory but with 0 quantity

            boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
            if(allProductsInStock){
                Order returnedOrder =  orderRepository.save(order);
                //kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(returnedOrder.getOrderNumber()));
                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, order.getOrderNumber()));
                return returnedOrder;
            }else {
                throw new IllegalArgumentException("Product is not in stock");
            }
        });
    }

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order with ID" + orderId + "not found in database!"));
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
