package org.example.ecomerce.Service.CartItem;

import org.example.ecomerce.Entity.CartItem;

import java.util.List;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long CartId,Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int Quantity);
    CartItem getCartItemByProductId(Long cartId, Long productId);
    List<CartItem> getAllCartItems();
}
