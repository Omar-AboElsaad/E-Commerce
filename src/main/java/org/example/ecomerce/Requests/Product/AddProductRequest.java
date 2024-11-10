package org.example.ecomerce.Requests.Product;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.ecomerce.Entity.Category;

import java.math.BigDecimal;

/**
 * This class represents Add productDTO in the E-commerce system.
 * It is same as product class ,but we use it to hide unnecessary data from product class
 * this also give us some security.
 * if you do not remember DTO Pattern go learn it and come back
 */

@RequiredArgsConstructor
@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal Price;
    private int Stock;
    private String Description;
    private Category category;
}
