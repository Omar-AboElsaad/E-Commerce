package org.example.ecomerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class represents a product in the E-commerce system.
 * It holds details such as name, price, and category.
 * It Also holds relation between category and Image
 */



@Setter
@Getter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int Stock;
    private String Description;

    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartItem>cartItems;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderItem>orderItems;

    public Product(String name, String brand, int stock, String description, BigDecimal price, Category category) {
        this.name=name;
        this.brand=brand;
        this.Stock=stock;
        this.Description=description;
        this.price=price;
        this.category=category;
    }
}
