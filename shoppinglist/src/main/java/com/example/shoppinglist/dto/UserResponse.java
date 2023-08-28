package com.example.shoppinglist.dto;

import com.example.shoppinglist.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    @JsonProperty("image")
    private CloudinaryImage cloudinaryImage;
    private boolean isLocked;
    private boolean isEnabled;
    private String token;

    public static UserResponse from(User user, String token) {
        return new UserResponse(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            new CloudinaryImage(
                user.getImage().getUrl(),
                user.getImage().getPublicId()),
            user.isLocked(),
            user.isEnabled(),
            token);
    }
}
