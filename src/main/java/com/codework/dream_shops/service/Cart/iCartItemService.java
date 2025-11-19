package com.codework.dream_shops.service.Cart;

import com.codework.dream_shops.Models.CartItem;

public interface iCartItemService {

    void addItemToCart(Long cartId,Long productId,
                       int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
