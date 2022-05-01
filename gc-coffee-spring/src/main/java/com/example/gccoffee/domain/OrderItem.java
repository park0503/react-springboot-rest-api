package com.example.gccoffee.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItem
{
    private final UUID orderId;
    private final UUID productId;
    private final Category category;
    private final Long price;
    private final Integer quantity;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderItem(UUID orderId, UUID productId, Category category, Long price, Integer quantity, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
