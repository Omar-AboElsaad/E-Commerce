package org.example.ecomerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal orderTotalAmounts=BigDecimal.ZERO;
    private String orderStatus;
    private LocalDate orderDate;
    private List<OrderItemDto> OrderItems;


}
