package com.marketplace.cart;

import com.marketplace.orderItem.OrderItem;

import java.util.UUID;

public class CartItemRequest {
    public UUID productId;
    public int quantity;

    public void setProductId(UUID productId){
        this.productId = productId;
    }

    public UUID getProductId(){
        return productId;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return quantity;
    }
}
