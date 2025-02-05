package org.example.order.entity;

import lombok.Data;

@Data
public class OrderItemEntity {
    int id;
    int orderId;
    float cost;
    CartItem item;
    @Data
    public static class CartItem{
        int productId;
        int quantity;
    }
}
