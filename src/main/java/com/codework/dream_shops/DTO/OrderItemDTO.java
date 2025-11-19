package com.codework.dream_shops.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
 private Long productId;
 private String productName;
 private int quantity;
 private BigDecimal price;
}
