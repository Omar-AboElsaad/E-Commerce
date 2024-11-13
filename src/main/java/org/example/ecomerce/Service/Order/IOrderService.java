package org.example.ecomerce.Service.Order;

import org.example.ecomerce.DTO.OrderDto;
import org.example.ecomerce.Entity.Orders;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    Orders getOrder(Long orderId);

    List<OrderDto> getAllUserOrdersDto(Long userId);

    List<Orders> getAllUserOrders(Long userId);

    //----------------------------------------------------------------------------------------------------------------------
    void DeleteOrder(Long userId, Long orderId);

    //----------------------------------------------------------------------------------------------------------------------
    OrderDto convertOrederToOrderDto(Orders orders);
}
