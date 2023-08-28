package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.CloudinaryImage;
import com.example.shoppinglist.dto.UploadImageRequest;
import com.example.shoppinglist.entity.Image;
import com.example.shoppinglist.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;
    private CloudinaryService cloudinaryService;

    public Image addImage(UploadImageRequest request) {
        Image image = createImage(request.getImage(), request.getTitle());
        return imageRepository.save(image);
    }

    @Transactional
    public Boolean deleteImage(String publicId) {
        if (cloudinaryService.delete(publicId)) {
            imageRepository.deleteByPublicId(publicId);
            return true;
        }

        return false;
    }

    private Image createImage(MultipartFile file, String title) {
        CloudinaryImage image = cloudinaryService.upload(file);

        return new Image(title, image.getUrl(), image.getPublicId());
    }
}
