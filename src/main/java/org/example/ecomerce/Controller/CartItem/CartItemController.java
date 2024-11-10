package org.example.ecomerce.Controller.CartItem;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.CartItem;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.CartItem.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController{
    private final CartItemService cartItemService;


//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/add-item-to-cart")
    public  ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                      @RequestParam Long productId,
                                                      @RequestParam int quantity) {
        try {
            cartItemService.addItemToCart( cartId, productId, quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Added Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

//----------------------------------------------------------------------------------------------------------------------


    @DeleteMapping("/remove-item-from-cart")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam Long cartId, @RequestParam Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item With ID "+itemId+" Removed!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


//----------------------------------------------------------------------------------------------------------------------


    @PutMapping("/update-item-quantity")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam Long cartId,
                                                          @RequestParam Long itemId,
                                                          @RequestParam int Quantity) {

        try {
            cartItemService.updateItemQuantity(cartId, itemId,  Quantity);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Quantity Updated Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

//----------------------------------------------------------------------------------------------------------------------


    @GetMapping("/getCartItemByItemId")
    public ResponseEntity<ApiResponse> getCartItemByItemId(@RequestParam Long cartId,@RequestParam Long productId) {
        try {
            CartItem cartItem= cartItemService.getCartItemByProductId(cartId,  productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Cart Item Found Successfully!",cartItem));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//----------------------------------------------------------------------------------------------------------------------


    @GetMapping("/all")
    public List<CartItem> getAll() {
       return cartItemService.getAllCartItems();
    }
}
