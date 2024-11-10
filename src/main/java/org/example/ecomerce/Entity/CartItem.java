package org.example.ecomerce.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal TotalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Cart_id")
    @JsonBackReference
    private Cart cart;


    public void setTotalPrice(){
        this.TotalPrice=this.unitPrice.multiply(new BigDecimal(quantity));
    }


}
