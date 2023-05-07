package com.rutgers.order.service.orderService.repository;

import com.rutgers.order.service.orderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
