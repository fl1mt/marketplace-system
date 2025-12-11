package com.marketplace.service;

import com.marketplace.dto.ProductRequestDTO;
import com.marketplace.dto.ProductResponseDTO;
import com.marketplace.entity.Product;
import com.marketplace.mapper.ProductMapper;
import com.marketplace.repository.ProductsRepository;
import com.marketplace.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;


    public ProductService(UsersRepository usersRepository, ProductsRepository productsRepository, ProductMapper productMapper) {
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;

    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO){
        System.out.println("DTO DESC: " + productRequestDTO.getDescription());

        Product product = productMapper.toEntity(productRequestDTO);

        System.out.println("ENTITY DESC: " + product.getDescription());

        Product savedProduct = productsRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProducts(){
        List<Product> products = productsRepository.findAll();
        return productMapper.toResponseDtoList(products);
    }

}
