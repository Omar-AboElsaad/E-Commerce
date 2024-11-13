package org.example.ecomerce.Controller.User;


import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.UserDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Requests.User.createUserRequest;
import org.example.ecomerce.Requests.User.updateUserRequest;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

//-------------------------------------------------------------------------------------------------


    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse> getUserById(@RequestParam Long userId) {
        try {
            User user=userService.getUserById(userId);
            UserDto userDto=userService.ConvertUserToUserDto(user);

           return ResponseEntity.ok()
                    .body(new ApiResponse("User Found!",userDto));
        }catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }


//-------------------------------------------------------------------------------------------------


    @PostMapping("/add-user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody createUserRequest request) {
        try {
            User user=userService.createUser(request);
            //Auto Generate Cart for New user
            user.setCart(cartService.createNewCart(user.getId()));
            //convert user to user DTO
            UserDto userDto=userService.ConvertUserToUserDto(user);
            return ResponseEntity.ok()
                    .body(new ApiResponse("User added successfully!",userDto));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }

    }


//-------------------------------------------------------------------------------------------------


    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody updateUserRequest request,@RequestParam Long userId) {
        try {
            User user=userService.updateUser(request,userId);
            UserDto userDto=userService.ConvertUserToUserDto(user);
            return ResponseEntity.ok()
                    .body(new ApiResponse("User Updated Successfully!",userDto));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

//-------------------------------------------------------------------------------------------------


   @DeleteMapping("/Delete")
    public ResponseEntity<ApiResponse> removeUser(@RequestParam Long userId) {
        try {
            Cart cart=cartService.getCartByUserId(userId);
            cartService.deleteCart(cart.getId());
            userService.removeUser(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("User Deleted !",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
