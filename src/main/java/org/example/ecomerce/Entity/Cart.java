package org.example.ecomerce.Entity;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    private BigDecimal TotalAmounts=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true) //when Cart is removed All cartItem will remove also
    @JsonManagedReference
    private Set<CartItem> cartItems=new HashSet<>();  //Set is a collection that contains no duplicate elements

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
