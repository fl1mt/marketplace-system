package com.marketplace.cart;

import com.marketplace.errors.BadRequestException;
import com.marketplace.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    private final OrderService orderService;
    private final CartService cartService;

    public CheckoutService(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @Transactional
    public void checkout(UUID userId, CheckoutRequest checkoutRequest){
        Map<UUID, Integer> cart = cartService.getCart(userId);

        if(cart.isEmpty()){
            throw new BadRequestException("Cart is empty.");
        }

        orderService.createOrderFromCart(userId, checkoutRequest.deliveryAddress(), cart);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        cartService.clearCart(userId);
                    }
                }
        );
    }
}
