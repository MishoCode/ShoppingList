package com.example.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UploadImageRequest {
    private String title;
    private MultipartFile image;
}
