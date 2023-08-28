package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.CloudinaryImage;
import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;
    private CloudinaryService cloudinaryService;

    public Image addImage(UploadImageRequest request) throws IOException {
        Image image = createImage(request.getImage(), request.getTitle());
        return imageRepository.save(image);
    }

    private Image createImage(MultipartFile file, String title) throws IOException {
        CloudinaryImage image = cloudinaryService.upload(file);

        return new Image(title, image.getUrl(), image.getPublicId());
    }
}
