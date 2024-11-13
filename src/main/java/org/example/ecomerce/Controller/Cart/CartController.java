package org.example.ecomerce.Controller.Cart;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.CartDto;
import org.example.ecomerce.DTO.CartItemDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final UserService userService;

//--------------------------------------------------------------------------------------------------


    @PostMapping("/create")
    public Cart createNewCart(Long userId){
        return cartService.createNewCart(userId);
    }

//--------------------------------------------------------------------------------------------------


    @GetMapping("/view-cart")
    public ResponseEntity<ApiResponse> viewCart(@RequestParam Long userId) {
       try {
           User user=userService.getUserById(userId);
            Cart cart = cartService.getcart(user.getCart().getId());
            CartDto cartDto=modelMapper.map(cart,CartDto.class);
            cartDto.setCartItems(new HashSet<>(convertCartItemToCartItemDto(cart.getCartItems())));
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Found!", cartDto));
        }catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
       }
    }


//--------------------------------------------------------------------------------------------------


    @DeleteMapping("/clear-cart")
    public  ResponseEntity<ApiResponse> clearCart(@RequestParam Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart With ID "+ cartId +" Clear Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }


    }

//--------------------------------------------------------------------------------------------------

    @DeleteMapping("/delete-cart")
    public  ResponseEntity<ApiResponse> deleteCart(@RequestParam Long id) {
        try {
            cartService.deleteCart(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart With ID "+id+" Deleted Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    //--------------------------------------------------------------------------------------------------


    @GetMapping("/view-cart-by-user-id")
    public ResponseEntity<ApiResponse> viewCartByUserId(@RequestParam Long userId) {
        try {
            CartDto cart = cartService.getCartDTOByUserId(userId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Found!", cart));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Helper Function
    private List<CartItemDto> convertCartItemToCartItemDto(Set<CartItem> cartItems){
        return cartItems.stream().map(item -> modelMapper.map(item, CartItemDto.class)).toList();
    }

}
