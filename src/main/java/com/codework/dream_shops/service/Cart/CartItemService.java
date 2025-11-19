package com.codework.dream_shops.service.Cart;

import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.Cart;
import com.codework.dream_shops.Models.CartItem;
import com.codework.dream_shops.Models.Product;
import com.codework.dream_shops.Repository.CartItemRepository;
import com.codework.dream_shops.Repository.CartRepository;
import com.codework.dream_shops.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CartItemService implements iCartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //get cart
        // get product
        //check if product already in cart
        //if yes increase the quantity
        //if no inititate new cartitem
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductsById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart=cartService.getCart(cartId);
        cart.getItems().stream()
                .filter(a->a.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(a->{
                    a.setQuantity(quantity);
                    a.setUnitPrice(a.getProduct().getPrice());
                    a.setTotalPrice();
                });
        BigDecimal totalAmount=cart.getItems().stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

}
