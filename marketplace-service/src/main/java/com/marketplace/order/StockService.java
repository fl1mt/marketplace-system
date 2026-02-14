package com.marketplace.order;

import com.marketplace.errors.BadRequestException;
import com.marketplace.orderItem.OrderItem;
import com.marketplace.product.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockService {
    private final ProductsRepository productsRepository;

    public StockService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional
    public void reserveStock(List<OrderItem> items) {
        for (OrderItem item : items) {
            int updated = productsRepository.reserveStock(
                    item.getProduct().getId(), item.getQuantity());

            if(updated == 0){
                throw new BadRequestException("Not enough stock for product. Product id: " +
                        item.getProduct().getId());
            }
        }
    }

    @Transactional
    public void returnStockWhenOrderCanceled(List<OrderItem> items){
        for (OrderItem item : items) {
            int updated = productsRepository.returnStock(
                    item.getProduct().getId(), item.getQuantity());

            if(updated == 0){
                throw new BadRequestException("Product not found! Product id: " +
                        item.getProduct().getId());
            }
        }
    }
}
