package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.RegistrationRequest;
import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;

    public UserResponse register(RegistrationRequest request) {
        User user = new User(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            UserRole.USER);

        return userService.signUpUser(user);
    }
}
