package com.example.shoppinglist.controller;

import com.example.shoppinglist.dto.RegistrationRequest;
import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/users/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestPart RegistrationRequest request, @RequestPart
                                                     MultipartFile image) {
        return ResponseEntity.ok(registrationService.register(request, image));
    }

    @GetMapping("/confirm")
    public ResponseEntity<Boolean> confirm(@RequestParam String token) {
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }

}
