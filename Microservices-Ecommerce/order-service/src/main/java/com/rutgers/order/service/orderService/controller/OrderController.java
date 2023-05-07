package com.rutgers.order.service.orderService.controller;

import com.rutgers.order.service.orderService.OrderServiceApplication;
import com.rutgers.order.service.orderService.dto.OrderRequest;
import com.rutgers.order.service.orderService.model.Order;
import com.rutgers.order.service.orderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name="inventory")
//    @Retry(name="inventory")
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.placeOrder(orderRequest);
        //return CompletableFuture.supplyAsync(() ->ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderRequest)));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    //public CompletableFuture<ResponseEntity<Order>> placeOrder(@RequestBody OrderRequest orderRequest){

    public CompletableFuture<ResponseEntity<String>> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        //Order order = new Order();
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong"));
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId){
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrder(){
        List<Order> orderList = orderService.getAllOrders();
        return ResponseEntity.ok(orderList);
    }
}
