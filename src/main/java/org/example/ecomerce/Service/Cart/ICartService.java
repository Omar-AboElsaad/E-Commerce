package org.example.ecomerce.Service.Cart;

import org.example.ecomerce.DTO.CartDto;
import org.example.ecomerce.Entity.Cart;
import org.example.ecomerce.Entity.CartItem;

import java.math.BigDecimal;

public interface ICartService {

    public Cart getcart(Long id);

    Long createNewCart(Long userId);

    public void clearCart(Long id);

    void deleteCart(Long id);

    public BigDecimal getTotalPrice(Long id);

    void addItemToCart(Cart cart, CartItem item);

    void removeItemFromCart(Cart cart, CartItem item);

    //This Method Used in anywhere we change in cart items because it recalculates Total Prices for all Cart Items
    void updateCartTotalAmount(Cart cart);

    CartDto getCartByUserId(Long userId);
}
