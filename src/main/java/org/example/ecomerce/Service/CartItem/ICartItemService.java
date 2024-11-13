package org.example.ecomerce.Service.CartItem;

import org.example.ecomerce.DTO.CartItemDto;

import java.util.List;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long CartId,Long itemId);
    void updateItemQuantity(Long cartId, Long itemId, int Quantity);
    CartItemDto getCartItemByProductId(Long cartId, Long productId);
    List<CartItemDto> getAllCartItems(Long cartId);

    CartItemDto getCartItemByProductname(Long userId, String productName);
}
