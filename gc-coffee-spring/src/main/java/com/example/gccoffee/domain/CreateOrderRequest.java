package com.example.gccoffee.domain;

import java.util.List;

public class CreateOrderRequest {
    private String email;
    private String address;
    private String postcode;
    List<OrderItem> orderItems;

    public CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
