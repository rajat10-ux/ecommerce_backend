package com.codework.dream_shops.service.User;

import com.codework.dream_shops.DTO.UserDto;
import com.codework.dream_shops.Models.User;
import com.codework.dream_shops.Requests.CreateUserRequest;
import com.codework.dream_shops.Requests.UserUpdateRequest;

public interface iUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest user);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserTODto(User user);
}
