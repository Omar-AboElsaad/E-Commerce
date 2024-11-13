package org.example.ecomerce.Service.Cart;

import org.example.ecomerce.DTO.CartDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;

import java.math.BigDecimal;

public interface ICartService {

     Cart getcart(Long id);

    Cart createNewCart(Long userId);

     void clearCart(Long id);

    void deleteCart(Long id);

     BigDecimal getTotalPrice(Long id);

    void addItemToCart(Cart cart, CartItem item);

    void removeItemFromCart(Cart cart, CartItem item);

    //This Method Used in anywhere we change in cart items because it recalculates Total Prices for all Cart Items
    void updateCartTotalAmount(Cart cart);

    CartDto getCartDTOByUserId(Long userId);
}
