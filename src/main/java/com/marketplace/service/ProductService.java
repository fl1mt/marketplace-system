package com.marketplace.service;
import com.marketplace.dto.ProductRequestDTO;
import com.marketplace.dto.ProductResponseDTO;
import com.marketplace.entity.Product;
import com.marketplace.mapper.ProductMapper;
import com.marketplace.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    private final ProductDiscountService productDiscountService;


    public ProductService(ProductsRepository productsRepository, ProductMapper productMapper, ProductDiscountService productDiscountService) {
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
        this.productDiscountService = productDiscountService;
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO){
        Product product = productMapper.toEntity(productRequestDTO);
        product.setFinalPrice(productDiscountService.calculateFinalPrice(product));
        Product savedProduct = productsRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProducts(){
        List<Product> products = productsRepository.findAll();
        return productMapper.toResponseDtoList(products);
    }

    public ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO){
        Product product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        if (isSame(product, productRequestDTO)) {
            throw new IllegalArgumentException("No changes detected");
        }

        productMapper.updateEntityFromDto(productRequestDTO, product);
        product.setFinalPrice(productDiscountService.calculateFinalPrice(product));
        productsRepository.save(product);
        return productMapper.toResponseDTO(product);
    }

    private boolean isSame(Product product, ProductRequestDTO dto) {
        return Objects.equals(product.getName(), dto.getName())
                && Objects.equals(product.getDescription(), dto.getDescription())
                && Objects.equals(product.getBasePrice(), dto.getBasePrice())
                && Objects.equals(product.getDiscountPercentage(), dto.getDiscountPercentage())
                && Objects.equals(product.getCategory(), dto.getCategory())
                && Objects.equals(product.getInventoryStock(), dto.getInventoryStock());
    }




}
