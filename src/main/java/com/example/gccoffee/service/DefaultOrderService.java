package com.example.gccoffee.service;

import com.example.gccoffee.domain.Email;
import com.example.gccoffee.domain.Order;
import com.example.gccoffee.domain.OrderItem;
import com.example.gccoffee.domain.OrderStatus;
import com.example.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService{

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(UUID.randomUUID(), email, address, postcode, orderItems, OrderStatus.ACCEPTED, LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0));
        return orderRepository.insert(order);
    }
}
