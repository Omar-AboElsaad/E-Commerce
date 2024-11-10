package org.example.ecomerce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

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
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;


    public void setTotalPrice(){
        this.TotalPrice=this.unitPrice.multiply(new BigDecimal(quantity));
    }

    public OrderItem(Product product, Orders orders, int quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.product = product;
        this.orders = orders;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        TotalPrice = totalPrice;
    }
}
