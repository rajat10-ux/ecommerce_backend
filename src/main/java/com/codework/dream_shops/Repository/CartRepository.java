package com.codework.dream_shops.Repository;

import com.codework.dream_shops.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
