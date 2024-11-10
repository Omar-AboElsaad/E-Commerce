package org.example.ecomerce.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal TotalPrice;
}
