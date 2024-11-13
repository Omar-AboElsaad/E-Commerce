package org.example.ecomerce.Controller.CartItem;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.CartItemDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.CartItem.CartItemService;
import org.example.ecomerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController{
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;


//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/add-item-to-cart")
    public  ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long userId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {
        try {
            //First Check if The user exists
            User user= userService.getUserById(userId);

            cartItemService.addItemToCart( user.getId(), productId, quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Added Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

//----------------------------------------------------------------------------------------------------------------------


    @DeleteMapping("/remove-item-from-cart")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam Long userId, @RequestParam Long itemId) {
        try {
            //First Check if The user exists
            User user= userService.getUserById(userId);

            cartItemService.removeItemFromCart(user.getId(),itemId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item With ID "+itemId+" Removed!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


//----------------------------------------------------------------------------------------------------------------------


    @PutMapping("/update-item-quantity")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam Long userId,
                                                          @RequestParam Long itemId,
                                                          @RequestParam int Quantity) {

        try {
            //First Check if The user exists
            User user= userService.getUserById(userId);

            cartItemService.updateItemQuantity(user.getId(), itemId,  Quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Quantity Updated Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

//----------------------------------------------------------------------------------------------------------------------


    @GetMapping("/getCartItemByItemId")
    public ResponseEntity<ApiResponse> getCartItemByProductId(@RequestParam Long userId,@RequestParam Long productId) {
        try {
            //First Check if The user exists
            User user= userService.getUserById(userId);

            CartItemDto cartItem= cartItemService.getCartItemByProductId(user.getId(),  productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Found Successfully!",cartItem));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//----------------------------------------------------------------------------------------------------------------------


    @GetMapping("/get-CartItem-By-product-name")
    public ResponseEntity<ApiResponse> getCartItemByProductName(@RequestParam Long userId,@RequestParam String productName) {
        try {
            //First Check if The user exists
            User user= userService.getUserById(userId);

            //Second get Product if exists
            CartItemDto cartItem= cartItemService.getCartItemByProductname(user.getId(),  productName);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Found Successfully!",cartItem));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all")
    public List<CartItemDto> getAll(Long userId) {
        //First Check if The user exists
        User user= userService.getUserById(userId);

        Cart cart=cartService.getCartByUserId(user.getId());
        return cartItemService.getAllCartItems(cart.getId());
    }
}
