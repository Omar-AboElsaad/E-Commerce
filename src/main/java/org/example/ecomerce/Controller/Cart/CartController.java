package org.example.ecomerce.Controller.Cart;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceAlreadyExistException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final UserService userService;

//--------------------------------------------------------------------------------------------------

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNewCart(Long userId){
        try {
          Cart cart=cartService.createNewCart(userId);
            return ResponseEntity.ok().body(new ApiResponse("Cart Created Successfully",cart));
        } catch (ResourceAlreadyExistException e) {
           return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

//--------------------------------------------------------------------------------------------------


    @GetMapping("/view-cart")
    public ResponseEntity<ApiResponse> viewCart() {
       try {
           User user=userService.getAuthenticatedUser();
            Cart cart = cartService.getcart(user.getCart().getId());
            CartDto cartDto=modelMapper.map(cart,CartDto.class);
            cartDto.setCartItems(new HashSet<>(convertCartItemToCartItemDto(cart.getCartItems())));
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Found!", cartDto));
        }catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
       }catch (JwtException e){
           return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
       }
    }


//--------------------------------------------------------------------------------------------------


    @DeleteMapping("/clear-cart")
    public  ResponseEntity<ApiResponse> clearCart() {
        try {
            User user=userService.getAuthenticatedUser();
            Long cartId=user.getCart().getId();
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
    public  ResponseEntity<ApiResponse> deleteCart() {
        try {
            User user=userService.getAuthenticatedUser();
            Long cartId=user.getCart().getId();
            cartService.deleteCart(cartId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart With ID "+cartId+" Deleted Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    //--------------------------------------------------------------------------------------------------

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
