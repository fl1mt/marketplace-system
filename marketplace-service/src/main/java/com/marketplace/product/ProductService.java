package com.marketplace.product;
import com.marketplace.errors.BadRequestException;
import com.marketplace.errors.NotFoundException;
import com.marketplace.product.productDiscount.ProductDiscountService;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;
    private final ProductDiscountService productDiscountService;
    private final DataAuthService dataAuthService;


    public ProductService(ProductsRepository productsRepository, ProductMapper productMapper, ProductDiscountService productDiscountService, DataAuthService dataAuthService) {
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
        this.productDiscountService = productDiscountService;
        this.dataAuthService = dataAuthService;
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
        return productMapper.toResponseDtoList(productsRepository.findAll());
    }

    public ProductResponseDTO getProduct(UUID productId){
        Product product = dataAuthService.checkProduct(productId);
        return productMapper.toResponseDTO(product);
    }

    public ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO){
        Product product = dataAuthService.checkProduct(productId);

        if (isSame(product, productRequestDTO)) {
            throw new BadRequestException("No changes detected");
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
