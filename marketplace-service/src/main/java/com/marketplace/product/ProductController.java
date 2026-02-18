package com.marketplace.product;

import com.marketplace.product.stock.StockAdjustmentRequest;
import com.marketplace.product.stock.StockService;
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
    private final StockService stockService;
    public ProductController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("productId") UUID productId){
        return ResponseEntity.ok(productService.getProduct(productId));
    }
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("productId") UUID productId, @RequestBody @Valid ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.updateProduct(productId, productRequestDTO));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO){
        return ResponseEntity.ok(productService.createProduct(productRequestDTO));
    }

    @PostMapping("/{productId}/stock-adjustments")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> adjustProductStock(@PathVariable("productId") UUID productId, @RequestBody @Valid StockAdjustmentRequest request){
        stockService.adjustStock(productId, request.amount());
        return ResponseEntity.ok().build();
    }
}
