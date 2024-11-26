package org.example.ecomerce.DTO;

import lombok.Data;
import org.example.ecomerce.Entity.Role;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDto cart;
    private List<OrderDto> order;
    private List<Role> roles;
}
