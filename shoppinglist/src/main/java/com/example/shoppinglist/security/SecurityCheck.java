package com.example.shoppinglist.security;

import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityCheck {

    private UserRepository userRepository;

    public boolean check(Long userId, Authentication authentication) {
        String name = authentication.getName();
        Optional<User> userOptional = userRepository.findByEmail(name);
        if (userOptional.isEmpty()) {
            return false;
        }

        return userOptional.get().getId().equals(userId);
    }
}
