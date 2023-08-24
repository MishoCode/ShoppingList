package com.example.shoppinglist.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
