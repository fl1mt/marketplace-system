package com.marketplace.product;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-06T05:53:41+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( requestDTO.getName() );
        product.setDescription( requestDTO.getDescription() );
        product.setDiscountPercentage( requestDTO.getDiscountPercentage() );
        product.setInventoryStock( requestDTO.getInventoryStock() );
        product.setBasePrice( requestDTO.getBasePrice() );
        product.setCategory( requestDTO.getCategory() );

        return product;
    }

    @Override
    public ProductResponseDTO toResponseDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        productResponseDTO.setDescription( product.getDescription() );
        productResponseDTO.setBasePrice( product.getBasePrice() );
        productResponseDTO.setName( product.getName() );
        productResponseDTO.setId( product.getId() );
        productResponseDTO.setCategory( product.getCategory() );
        productResponseDTO.setDiscountPercentage( product.getDiscountPercentage() );
        productResponseDTO.setInventoryStock( product.getInventoryStock() );
        productResponseDTO.setAverageRating( product.getAverageRating() );
        productResponseDTO.setReviewCount( product.getReviewCount() );
        productResponseDTO.setFinalPrice( product.getFinalPrice() );

        return productResponseDTO;
    }

    @Override
    public ProductSnapshotDTO toSnapshotDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductSnapshotDTO productSnapshotDTO = new ProductSnapshotDTO();

        productSnapshotDTO.setId( product.getId() );
        productSnapshotDTO.setName( product.getName() );

        return productSnapshotDTO;
    }

    @Override
    public void updateEntityFromDto(ProductRequestDTO dto, Product product) {
        if ( dto == null ) {
            return;
        }

        product.setName( dto.getName() );
        product.setDescription( dto.getDescription() );
        product.setDiscountPercentage( dto.getDiscountPercentage() );
        product.setInventoryStock( dto.getInventoryStock() );
        product.setBasePrice( dto.getBasePrice() );
        product.setCategory( dto.getCategory() );
    }

    @Override
    public List<ProductResponseDTO> toResponseDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponseDTO> list = new ArrayList<ProductResponseDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toResponseDTO( product ) );
        }

        return list;
    }
}
