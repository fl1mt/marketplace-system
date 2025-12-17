package com.marketplace.controller;

import com.marketplace.dto.OrderRequestDTO;
import com.marketplace.dto.OrderResponseDTO;
import com.marketplace.security.CustomUserDetails;
import com.marketplace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/orders")
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
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(orderService.getUsersOrders(userDetails.getId()));
    }
}
