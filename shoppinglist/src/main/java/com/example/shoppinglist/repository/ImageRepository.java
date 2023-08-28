package com.example.shoppinglist.repository;

import com.example.shoppinglist.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    void deleteByPublicId(String publicId);
}
