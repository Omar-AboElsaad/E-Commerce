package org.example.ecomerce.Service.Order;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.CartDto;
import org.example.ecomerce.DTO.OrderDto;
import org.example.ecomerce.DTO.OrderItemDto;
import org.example.ecomerce.Entity.*;
import org.example.ecomerce.Repository.OrderRepo;
import org.example.ecomerce.Repository.ProductRepo;
import org.example.ecomerce.Service.Cart.CartService;
import org.example.ecomerce.Service.Product.ProductService;
import org.example.ecomerce.Service.User.UserService;
import org.example.ecomerce.enums.OrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService{
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductService productService;

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public OrderDto placeOrder(Long userId) {
       CartDto cart=cartService.getCartByUserId(userId);
       Orders orders = createOrder(cart);
       List <OrderItem> orderItem=createOrderItems(cart, orders);
       orders.setOrderItems(new HashSet<>(orderItem));
       orders.setOrderTotalAmounts(calculateTotalAmount(orders));
       orderRepo.save(orders);
       cartService.clearCart(cart.getId());
       OrderDto orderDto=convertOrederToOrderDto(orders);
       orderDto.setOrderItemsDTO(new HashSet<>(convertOrderItemToOrderItemDto(orderItem)));
        return orderDto;
    }

//----------------------------------------------------------------------------------------------------------------------


    private Orders createOrder(CartDto cart){
        Orders orders =new Orders();
        orders.setOrderStatus(OrderStatus.PENDING);
        User user=userService.getUserById(cart.getUserId());
        orders.setUser(user);
        orders.setOrderDate(LocalDate.now());

     return orders;
    }


//----------------------------------------------------------------------------------------------------------------------

    private List<OrderItem> createOrderItems(CartDto cart, Orders orders){
      return cart.getCartItemsDto().stream().map(item -> {
                Product product=productService.getProductById(item.getProductId());
                product.setStock(product.getStock()-item.getQuantity());
                productRepo.save(product);
               return new OrderItem(
                       product,
                       orders,
                       item.getQuantity(),
                       item.getUnitPrice(),
                       item.getTotalPrice()
               );

            }).toList();

    }


//----------------------------------------------------------------------------------------------------------------------


    private BigDecimal calculateTotalAmount(Orders orders){
       return orders.getOrderItems().stream().map(orderItem ->
          orderItem.getUnitPrice()
                  .multiply(new BigDecimal(orderItem.getQuantity())))
        .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

//----------------------------------------------------------------------------------------------------------------------


    @Override
    public Orders getOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no order with id "+orderId));
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<OrderDto> getAllUserOrdersDto(Long userId){
       List<Orders> orders= orderRepo.findAllByUserId(userId);
      return orders.stream().map(order -> {
        OrderDto orderDto=  convertOrederToOrderDto(order);
        List<OrderItemDto> orderItemDto=  convertOrderItemToOrderItemDto(order.getOrderItems().stream().toList()) ;
        orderDto.setOrderItemsDTO(new HashSet<>(orderItemDto));
        return orderDto;
      } ).toList();
    }

    @Override
    public List<Orders> getAllUserOrders(Long userId){
       return orderRepo.findAllByUserId(userId);

    }

//----------------------------------------------------------------------------------------------------------------------

    public void DeleteOrder(Long userId,Long orderId){
       Orders orders= orderRepo.findByUserIdAndOrderId(userId,orderId)
               .orElseThrow(() -> new ResourceNotFoundException("There no Order with id "+orderId+" For This user"));
       orderRepo.delete(orders);
    }
//----------------------------------------------------------------------------------------------------------------------
    public OrderDto convertOrederToOrderDto(Orders orders ){
       return modelMapper.map(orders,OrderDto.class);
    }

    public List<OrderItemDto> convertOrderItemToOrderItemDto(List<OrderItem> orderItem){
        return orderItem.stream().map(item -> modelMapper.map(item, OrderItemDto.class)).toList();
    }
//----------------------------------------------------------------------------------------------------------------------
}
