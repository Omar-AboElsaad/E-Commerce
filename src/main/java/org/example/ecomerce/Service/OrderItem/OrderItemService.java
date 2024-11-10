package org.example.ecomerce.Service.OrderItem;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.ecomerce.DTO.OrderDto;
import org.example.ecomerce.Entity.OrderItem;
import org.example.ecomerce.Entity.Orders;
import org.example.ecomerce.Repository.OrderItemRepo;
import org.example.ecomerce.Repository.OrderRepo;
import org.example.ecomerce.Service.Order.OrderService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@AllArgsConstructor
@Service
public class OrderItemService implements IOrderItemService {
    private final OrderRepo orderRepo;

    @Override
    @Transactional
    public void deleteOrderItemById(Long itemId, Long userId) {
       List<Orders> orders=orderRepo.findAllByUserId(userId);
      orders.forEach(order ->{
          List <OrderItem> orderItems= order.getOrderItems().stream().filter(Item -> !Item.getId().equals(itemId)).toList();
          order.setOrderItems(new HashSet<>(orderItems));
      });

      orderRepo.saveAll(orders);
    }

    @Override
    public void deleteAll(Long userId) {

    }

    @Override
    public void updateQuantity(Long userId) {

    }


}