package com.codework.dream_shops.service.Order;

import com.codework.dream_shops.DTO.OrderDTO;
import com.codework.dream_shops.Models.Order;

import java.util.List;

public interface iOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);
}
