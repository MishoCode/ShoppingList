package com.example.shoppinglist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {
    @NotNull
    @Size(min = 3, message = "The password should be at least 2 symbols")
    private String firstName;

    @NotNull
    @Size(min = 3, message = "The lastName should be at least 2 symbols")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Size(min = 3, message = "The password should be at least 3 symbols")
    private String password;
}
