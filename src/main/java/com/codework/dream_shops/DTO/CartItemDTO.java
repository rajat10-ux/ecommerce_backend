package com.codework.dream_shops.DTO;

import com.codework.dream_shops.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
