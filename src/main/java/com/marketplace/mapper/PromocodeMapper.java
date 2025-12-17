package com.marketplace.mapper;

import com.marketplace.dto.ProductRequestDTO;
import com.marketplace.dto.ProductResponseDTO;
import com.marketplace.entity.Product;
import com.marketplace.entity.Promocode;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromocodeMapper {
    Promocode toEntity(ProductRequestDTO requestDTO);
    ProductResponseDTO toResponseDTO(Product product);
    List<ProductResponseDTO> toResponseDtoList(List<Product> products);
}
