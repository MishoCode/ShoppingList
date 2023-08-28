package com.example.shoppinglist.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImageRequest {
    private String title;
    private MultipartFile image;
}
