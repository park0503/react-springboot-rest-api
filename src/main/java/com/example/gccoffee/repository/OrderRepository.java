package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Order;

public interface OrderRepository {
    Order insert(Order order);
}
