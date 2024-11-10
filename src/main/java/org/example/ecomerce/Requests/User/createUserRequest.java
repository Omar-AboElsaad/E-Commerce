package org.example.ecomerce.Requests.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
