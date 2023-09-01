package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.entity.VerificationToken;
import com.example.shoppinglist.exception.UserAlreadyExistsException;
import com.example.shoppinglist.exception.UserNotFoundException;
import com.example.shoppinglist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;

    public UserResponse signUpUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (!dbUser.isEnabled() && hasTheSameFields(dbUser, user)) {
                String newToken = generateToken(dbUser);
                return UserResponse.from(dbUser, newToken);
            }

            if (dbUser.isEnabled() || !hasTheSameFields(dbUser, user)) {
                throw new UserAlreadyExistsException(
                    String.format("Email %s has already been taken.", user.getEmail()));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        String token = generateToken(user);

        return UserResponse.from(savedUser, token);
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    private String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusSeconds(60),
            user);
        verificationTokenService.saveToken(verificationToken);

        return token;
    }

    private boolean hasTheSameFields(User dbUser, User requestUser) {
        return dbUser.getFirstName().equals(requestUser.getFirstName()) &&
               dbUser.getLastName().equals(requestUser.getLastName()) &&
               dbUser.getEmail().equals(requestUser.getEmail()) &&
               passwordEncoder.matches(requestUser.getPassword(), dbUser.getPassword());
    }
}
