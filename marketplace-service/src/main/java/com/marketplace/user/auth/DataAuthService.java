package com.marketplace.user.auth;
import com.marketplace.delivery.DeliveryAddress;
import com.marketplace.errors.BadRequestException;
import com.marketplace.errors.NotFoundException;
import com.marketplace.delivery.DeliveryAddressesRepository;
import com.marketplace.order.Order;
import com.marketplace.order.OrdersRepository;
import com.marketplace.orderItem.OrderItem;
import com.marketplace.orderItem.OrderItemsRepository;
import com.marketplace.product.Product;
import com.marketplace.product.ProductsRepository;
import com.marketplace.user.User;
import com.marketplace.user.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DataAuthService {
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final DeliveryAddressesRepository deliveryAddressesRepository;
    private final OrderItemsRepository orderItemsRepository;
    public DataAuthService(UsersRepository usersRepository, OrdersRepository ordersRepository, ProductsRepository productsRepository, DeliveryAddressesRepository deliveryAddressesRepository, OrderItemsRepository orderItemsRepository) {
        this.usersRepository = usersRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.deliveryAddressesRepository = deliveryAddressesRepository;
        this.orderItemsRepository = orderItemsRepository;
    }
    public User checkUsersId(UUID userId){
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Invalid User data!"));
        return user;
    }
    public DeliveryAddress checkUsersDeliveryAddress(UUID deliveryAddressId, UUID userId){
        DeliveryAddress deliveryAddress = deliveryAddressesRepository.findByIdAndUserId(deliveryAddressId, userId)
                .orElseThrow(() -> new NotFoundException("Address not found or access denied"));
        return deliveryAddress;
    }

    public Order checkOrdersAffiliation(UUID orderId, UUID userId){
        Order order = ordersRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new NotFoundException("Order not found or access denied!")
                );
        return order;
    }

    public Order checkOrder(UUID orderId){
        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found or access denied!")
                );
        return order;
    }

    public List<OrderItem> checkOrderItemsByOrder(UUID orderId){
        List<OrderItem> orderItems = orderItemsRepository.findByOrderIdWithProducts(orderId);
        if(orderItems == null || orderItems.isEmpty()){
            throw new BadRequestException("Order must contain at least one item");
        }
        return orderItems;
    }

    public Product checkProduct(UUID productId){
        Product product = productsRepository.findById(productId)
                .orElseThrow(() ->
                        new NotFoundException("Product not found, id=" + productId)
                );
        return product;
    }
}
