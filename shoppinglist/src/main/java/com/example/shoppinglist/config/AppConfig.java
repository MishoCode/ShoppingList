package com.example.shoppinglist.config;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class AppConfig {

    private CloudinaryConfig cloudinaryConfig;

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
}
