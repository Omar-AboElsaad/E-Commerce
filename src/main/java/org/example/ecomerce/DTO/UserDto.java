package org.example.ecomerce.DTO;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDto cart;
    private List<OrderDto> order;
}
