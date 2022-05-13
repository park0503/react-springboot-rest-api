package com.example.gccoffee.service;

import com.example.gccoffee.domain.Email;
import com.example.gccoffee.domain.Order;
import com.example.gccoffee.domain.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
