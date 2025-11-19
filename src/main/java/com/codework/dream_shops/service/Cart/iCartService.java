package com.codework.dream_shops.service.Cart;

import com.codework.dream_shops.Models.Cart;

import java.math.BigDecimal;

public interface iCartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
