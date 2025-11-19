package com.codework.dream_shops.service.Order;

import com.codework.dream_shops.DTO.OrderDTO;
import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.Cart;
import com.codework.dream_shops.Models.Order;
import com.codework.dream_shops.Models.OrderItem;
import com.codework.dream_shops.Models.Product;
import com.codework.dream_shops.Repository.OrderRepository;
import com.codework.dream_shops.Repository.ProductRepository;
import com.codework.dream_shops.enums.OrderStatus;
import com.codework.dream_shops.service.Cart.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService implements iOrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order=createOrder(cart);
        List<OrderItem>orderItemList=createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder=orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return saveOrder;
    }

    private Order createOrder(Cart cart){
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem>createOrderItems(Order order, Cart cart){
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product=cartItem.getProduct();
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice());
                }).toList();
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
       return orderRepository.findById(orderId)
               .map(this::convertToDTO)
               .orElseThrow(()->new ResourceNotFoundException("Not found"));
    }

    @Override
    public List<OrderDTO>getUserOrders(Long userId){
       List<Order>orders=orderRepository.findByUserId(userId);
       return orders.stream().map(this::convertToDTO).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem>orderItemList){
        return orderItemList.stream().map(a->a.getUnitPrice()
                .multiply(new BigDecimal(a.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
    private OrderDTO convertToDTO(Order order){
        return modelMapper.map(order,OrderDTO.class);
    }
}




