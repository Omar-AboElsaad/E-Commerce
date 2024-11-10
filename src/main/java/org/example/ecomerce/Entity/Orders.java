package org.example.ecomerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ecomerce.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private BigDecimal orderTotalAmounts=BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDate orderDate;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL,orphanRemoval = true) //when Cart is removed All cartItem will remove also
    private Set<OrderItem> OrderItems=new HashSet<>();  //Set is a collection that contains no duplicate elements

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
