package com.marketplace.orderItem;

import com.marketplace.product.ProductMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-06T05:53:40+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderItem toEntity(OrderItemRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity( requestDTO.getQuantity() );
        orderItem.setCreatedAt( requestDTO.getCreatedAt() );
        orderItem.setUpdatedAt( requestDTO.getUpdatedAt() );

        return orderItem;
    }

    @Override
    public OrderItemResponseDTO toOrderItemDTO(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();

        orderItemResponseDTO.setProductSnapshotDTO( productMapper.toSnapshotDTO( orderItem.getProduct() ) );
        orderItemResponseDTO.setId( orderItem.getId() );
        orderItemResponseDTO.setQuantity( orderItem.getQuantity() );
        orderItemResponseDTO.setPriceAtPurchase( orderItem.getPriceAtPurchase() );

        return orderItemResponseDTO;
    }

    @Override
    public List<OrderItemResponseDTO> toOrderItemsListDTO(List<OrderItem> orderItems) {
        if ( orderItems == null ) {
            return null;
        }

        List<OrderItemResponseDTO> list = new ArrayList<OrderItemResponseDTO>( orderItems.size() );
        for ( OrderItem orderItem : orderItems ) {
            list.add( toOrderItemDTO( orderItem ) );
        }

        return list;
    }
}
