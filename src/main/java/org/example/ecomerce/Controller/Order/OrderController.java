package org.example.ecomerce.Controller.Order;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.OrderDto;
import org.example.ecomerce.Entity.Orders;
import org.example.ecomerce.Entity.User;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Order.OrderService;
import org.example.ecomerce.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

//-------------------------------------------------------------------------------------------------

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrder() {
          try {
              User user=userService.getAuthenticatedUser();
              OrderDto orderDto =orderService.placeOrder(user.getId());
                return ResponseEntity.ok().body(new ApiResponse("Order added Successfully!", orderDto));
            }catch (ResourceNotFoundException e){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }

    }

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public ResponseEntity<ApiResponse> getAllUserOrders() {
        try {
            User user=userService.getAuthenticatedUser();
           List<OrderDto> orders=orderService.getAllUserOrdersDto(user.getId());
            return ResponseEntity.ok().body(new ApiResponse("Orders Found!",orders));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @DeleteMapping("/delete-by-userid-and-orderid")
    public ResponseEntity<ApiResponse> deleteOrder(@RequestParam Long orderId){
        try {
            User user=userService.getAuthenticatedUser();
            orderService.DeleteOrder(user.getId(),orderId);
            return ResponseEntity.ok().body(new ApiResponse("Order Deleted Successfully!",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @DeleteMapping("/delete-all-user-orders")
    public ResponseEntity<ApiResponse> DeleteAll(){
        try {
            User user=userService.getAuthenticatedUser();
            userService.getUserById(user.getId());
            orderService.DeleteAll(user.getId());
            return ResponseEntity.ok().body(new ApiResponse("All orders Deleted !",null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
