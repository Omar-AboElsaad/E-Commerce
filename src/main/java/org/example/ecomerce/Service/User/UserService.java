package org.example.ecomerce.Service.User;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceAlreadyExistException;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.UserDto;
import org.example.ecomerce.Entity.Role;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Repository.RolesRepo;
import org.example.ecomerce.Repository.UserRepo;
import org.example.ecomerce.Requests.User.createUserRequest;
import org.example.ecomerce.Requests.User.updateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder   passwordEncoder;
    private final RolesRepo rolesRepo;

//-------------------------------------------------------------------------------------------------

//    @Override
//    public UserDto getUserDToById(Long userId) {
//        User user= userRepo.findById(userId)
//                .orElseThrow(()->new ResourceNotFoundException("There is no User With ID "+userId));
//        UserDto userDto= ConvertUserToUserDto(user);
//
//       List<Orders> orders=user.getOrder();
//
//       List<OrderDto> orderDtos= orders.stream().map(order ->{
//           OrderDto orderDto = orderService.convertOrederToOrderDto(order);
//           List<OrderItemDto> orderItemsDto=orderService.convertOrderItemToOrderItemDto(order.getOrderItems().stream().toList());
//           orderDto.setOrderItemsDTO(new HashSet<>(orderItemsDto));
//           return orderDto;
//       }).toList();
//       userDto.setOrder(orderDtos);
//       return userDto;
//    }

    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no User With ID " + userId));
    }

//-------------------------------------------------------------------------------------------------

    @Override
    public User createUser(createUserRequest request,String Role) {
        if (userRepo.existsByemail(request.getEmail())) {
            throw new ResourceAlreadyExistException(request.getEmail() + " Already Exist!");
        } else {
            User user = new User();
            Role NewUserRole=rolesRepo.findByName(Role).get();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setRoles(Set.of(NewUserRole));
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            return userRepo.save(user);
        }


    }


//-------------------------------------------------------------------------------------------------

    @Override
    public User updateUser(updateUserRequest request, Long userId) {
        return userRepo.findById(userId).map(exitingUser -> {
            exitingUser.setFirstName(request.getFirstName());
            exitingUser.setLastName(request.getLastName());
            return userRepo.save(exitingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("There is no user with Id " + userId));

    }


//-------------------------------------------------------------------------------------------------

    @Override
    public void removeUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete, () -> {
            throw new ResourceNotFoundException("There is no user with ID " + userId);
        });
    }

    @Override
    public UserDto ConvertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

//
//    private List<OrderItem> GetOrderItem(Orders orders){
//      return orderItemService.getAllOrderItemsByOrderId(orders.getId());
//    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByemail(email);
    }
}