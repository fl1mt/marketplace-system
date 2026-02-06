package com.marketplace.product;

import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products/")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.createProduct(productRequestDTO));
    }
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("productId") UUID productId, @RequestBody @Valid ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(productId, productRequestDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("productId") UUID productId){
        return ResponseEntity.ok(productService.getProduct(productId));
    }
}
