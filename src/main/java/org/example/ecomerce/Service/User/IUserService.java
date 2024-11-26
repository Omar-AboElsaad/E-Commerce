package org.example.ecomerce.Service.User;

import org.example.ecomerce.DTO.UserDto;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Requests.User.createUserRequest;
import org.example.ecomerce.Requests.User.updateUserRequest;

public interface IUserService {

     User getUserById(Long userId);

     User createUser(createUserRequest request,String role);

     User updateUser(updateUserRequest request, Long userId);

     void removeUser(Long userId);


    UserDto ConvertUserToUserDto(User user);

    User getAuthenticatedUser();
}
