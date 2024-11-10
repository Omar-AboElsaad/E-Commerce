package org.example.ecomerce.Controller.Order;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.OrderDto;
import org.example.ecomerce.Entity.Orders;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final OrderService orderService;

//-------------------------------------------------------------------------------------------------

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId) {
          try {
              OrderDto orderDto =orderService.placeOrder(userId);
                return ResponseEntity.ok().body(new ApiResponse("Order added Successfully!", orderDto));
            }catch (ResourceNotFoundException e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }

    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/get-by-id")
    public ResponseEntity<ApiResponse> getOrder(@RequestParam Long orderId) {
        try {
            Orders orders =orderService.getOrder(orderId);
           return ResponseEntity.ok().body(new ApiResponse("Order Found!", orders));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/get-all-orders")
    public ResponseEntity<ApiResponse> getAllUserOrders(@RequestParam Long userId) {
        try {
           List<OrderDto> orders=orderService.getAllUserOrdersDto(userId);
            return ResponseEntity.ok().body(new ApiResponse("Orders Found!",orders));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

//-------------------------------------------------------------------------------------------------
   @DeleteMapping("/delete-by-userid-and-orderid")
    public ResponseEntity<ApiResponse> deleteOrder(@RequestParam Long userId,@RequestParam Long orderId){
        try {
            orderService.DeleteOrder(userId,orderId);
            return ResponseEntity.ok().body(new ApiResponse("Order Deleted Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
