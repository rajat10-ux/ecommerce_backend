package com.codework.dream_shops.Controllers;

import com.codework.dream_shops.DTO.OrderDTO;
import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.Order;
import com.codework.dream_shops.Response.ApiResponse;
import com.codework.dream_shops.service.Order.OrderService;
import com.sun.security.auth.UnixNumericUserPrincipal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api_prefix}/orders")
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {
    @Autowired
private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse>createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order Success",order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse>getOrderById(@PathVariable  Long orderId){
        try {
            OrderDTO order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("iterm Order Success",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse>getUserOrder(@PathVariable  Long userId){
        try {
            List<OrderDTO> order=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("iterm Order Success",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
