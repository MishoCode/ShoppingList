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
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
            () -> new UserNotFoundException(
                String.format("User with username %s not found.", username)));
    }

    public UserResponse signUpUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException(
                String.format("Email %s has already been taken.", user.getEmail()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(30),
            user);
        verificationTokenService.saveToken(verificationToken);

        return UserResponse.from(savedUser, token);
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
