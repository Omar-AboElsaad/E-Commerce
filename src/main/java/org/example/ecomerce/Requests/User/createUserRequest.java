package org.example.ecomerce.Requests.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecomerce.Entity.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createUserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email(message = "Invalid Email")
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
