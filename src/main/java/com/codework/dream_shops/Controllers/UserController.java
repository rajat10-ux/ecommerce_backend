package com.codework.dream_shops.Controllers;

import com.codework.dream_shops.DTO.UserDto;
import com.codework.dream_shops.Exceptions.AlreadyExistsException;
import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.User;
import com.codework.dream_shops.Requests.CreateUserRequest;
import com.codework.dream_shops.Requests.UserUpdateRequest;
import com.codework.dream_shops.Response.ApiResponse;
import com.codework.dream_shops.service.User.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api_prefix}/users")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse>getUserById(@PathVariable  Long userId){
        try {
            User user=userService.getUserById(userId);
            UserDto userDto=userService.convertUserTODto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>createOrder(@RequestBody CreateUserRequest req){
        try {
            User user=userService.createUser(req);
            UserDto userDto=userService.convertUserTODto(user);
            return ResponseEntity.ok(new ApiResponse("Create a User",userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse>updateUser(@RequestBody UserUpdateRequest req,
                                                 @PathVariable  Long userId){
        try {
            User user=userService.updateUser(req,userId);
            UserDto userDto=userService.convertUserTODto(user);
            return ResponseEntity.ok(new ApiResponse("Update User",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Deleted Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
