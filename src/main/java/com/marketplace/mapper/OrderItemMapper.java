package com.marketplace.mapper;

import com.marketplace.dto.OrderItemRequestDTO;
import com.marketplace.dto.OrderItemResponseDTO;
import com.marketplace.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDTO requestDTO);
    @Mapping(target = "productSnapshotDTO", source = "product")
    OrderItemResponseDTO toOrderItemDTO(OrderItem orderItem);
    List<OrderItemResponseDTO> toOrderItemsListDTO(List<OrderItem> orderItems);
}
