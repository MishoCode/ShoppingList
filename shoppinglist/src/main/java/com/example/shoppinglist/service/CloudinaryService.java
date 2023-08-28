package com.example.shoppinglist.service;

import com.cloudinary.Cloudinary;
import com.example.shoppinglist.dto.CloudinaryImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("temp_file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> result = cloudinary.uploader().upload(tempFile, Map.of());

            String url = result.getOrDefault(
                "url",
                "https://png.pngtree.com/element_pic/16/11/14/4810d7467c24879d43006148e3c0c73a.jpg");

            String public_id = result.getOrDefault("public_id", "");

            return new CloudinaryImage(url, public_id);
        } finally {
            tempFile.delete();
        }

    }
}