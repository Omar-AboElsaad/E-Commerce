package org.example.ecomerce.Requests.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class  updateUserRequest {
    private String firstName;
    private String lastName;

}
