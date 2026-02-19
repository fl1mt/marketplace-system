
package com.marketplace.cart;

import com.marketplace.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CheckoutService checkoutService;

    public CartController(CartService cartService, CheckoutService checkoutService) {
        this.cartService = cartService;
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ResponseEntity.ok(cartService.getCart(user.getId()));
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody CartItemRequest request) {

        cartService.addToCart(user.getId(),
                request.getProductId(),
                request.getQuantity());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(
            @AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid CheckoutRequest checkoutRequest) {

        checkoutService.checkout(user.getId(), checkoutRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal CustomUserDetails user) {
        cartService.clearCart(user.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@AuthenticationPrincipal CustomUserDetails user, @PathVariable("productId") UUID productId) {
        cartService.removeItem(user.getId(), productId);
        return ResponseEntity.ok().build();
    }
}

