package com.example.shoppinglist.config;

import com.cloudinary.Cloudinary;
import com.example.shoppinglist.exception.UserNotFoundException;
import com.example.shoppinglist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class AppConfig {

    private CloudinaryConfig cloudinaryConfig;
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String username) -> userRepository.findByEmail(username).orElseThrow(
            () -> new UserNotFoundException(
                String.format("User with username %s not found.", username)));
    }

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(
            Map.of(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret()
            )
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
