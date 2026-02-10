package com.marketplace.order;

import com.marketplace.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrderByUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.ok(orderService.createUsersOrder(userDetails.getId(), orderRequestDTO));
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestParam(name = "onlyActiveOrders", defaultValue = "true")
                                                                boolean onlyActiveOrders){
        return ResponseEntity.ok(orderService.getUsersOrders(userDetails.getId(), onlyActiveOrders));
    }
}
