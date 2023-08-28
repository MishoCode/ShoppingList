package com.example.shoppinglist.controller;

import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;

    @PostMapping("/add")
    public ResponseEntity<Image> addImage(UploadImageRequest request) {
        try {
            return ResponseEntity.ok(imageService.addImage(request));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteImage(@RequestParam("public_id") String publicId) {
        if (imageService.deleteImage(publicId)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.internalServerError().build();
    }
}
