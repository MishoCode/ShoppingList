package com.example.shoppinglist.controller;

import com.example.shoppinglist.dto.UserResponse;
import com.example.shoppinglist.security.SecurityCheck;
import com.example.shoppinglist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private SecurityCheck securityCheck;

    @PatchMapping("/{id}/add-image")
    @PreAuthorize("@securityCheck.check(#userId, authentication)")
    public ResponseEntity<UserResponse> addImage(@PathVariable("id") Long userId,
                                           @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(userService.addImage(userId, image));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
