package org.example.ecomerce.Controller.OrderItem;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.OrderItem.IOrderItemService;
import org.example.ecomerce.Service.OrderItem.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order-item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @DeleteMapping("/Delete-by-id")
    public ResponseEntity<ApiResponse> deleteOrderItem(@RequestParam Long itemId,@RequestParam Long userId){
       try {
           orderItemService.deleteOrderItemById(itemId,userId);
           return ResponseEntity.ok().body(new ApiResponse("Deleted Successfully!",null));
       }catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
       }
    }
}
