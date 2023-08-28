package com.example.shoppinglist.controller;

import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;

    @PostMapping("/images/add")
    public ResponseEntity<Image> addPicture(UploadImageRequest request) {
        try {
            return ResponseEntity.ok(imageService.addImage(request));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
