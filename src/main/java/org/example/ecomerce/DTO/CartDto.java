package org.example.ecomerce.DTO;

import lombok.Data;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private BigDecimal TotalAmounts = BigDecimal.ZERO;
    private Set<CartItemDto> cartItems = new HashSet<>();  //Set is a collection that contains no duplicate elements
}
