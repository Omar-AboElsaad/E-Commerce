package org.example.ecomerce.DTO;

import lombok.Data;
import org.example.ecomerce.Entity.Category;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private String name;
    private String brand;
    private BigDecimal Price;
    private int Stock;
    private String Description;
    private Category category;
    private List<ImageDto> images;
}
