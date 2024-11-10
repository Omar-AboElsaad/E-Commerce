package org.example.ecomerce.Service.OrderItem;

public interface IOrderItemService {
   void deleteOrderItemById(Long itemId,Long userId);
   void deleteAll(Long userId);
   void updateQuantity(Long userId);
}
