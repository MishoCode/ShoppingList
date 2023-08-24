package com.example.shoppinglist.dto;

import com.example.shoppinglist.entity.User;
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
    private boolean isLocked;
    private boolean isEnabled;

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.isLocked(),
            user.isEnabled());
    }
}
