package org.example.ecomerce.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.Orders;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Cart cart;
    private Set<OrderDto> order;
}
