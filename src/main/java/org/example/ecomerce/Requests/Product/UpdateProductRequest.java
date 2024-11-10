package org.example.ecomerce.Requests.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecomerce.Entity.Category;
import java.math.BigDecimal;

/**
 * This class represents Update productDTO in the E-commerce system.
 * It only Holds necessary attribute .
 * It is same as product class ,but we use it to hide unnecessary data from product class
 * this also give us some security.
 * if you do not remember DTO Pattern go learn it and come back
 */



@NoArgsConstructor
@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal Price;
    private int Stock;
    private String Description;
    private Category category;
}
