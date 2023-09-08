package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.AuthenticationRequest;
import com.example.shoppinglist.dto.AuthenticationResponse;
import com.example.shoppinglist.entity.User;
import com.example.shoppinglist.exception.UserNotFoundException;
import com.example.shoppinglist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserRepository userRepository;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        ));

        User user = userRepository.findByEmail(request.getUsername()).orElseThrow(() ->
            new UserNotFoundException(
                String.format("User with username %s not found.", request.getUsername()))
        );

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
