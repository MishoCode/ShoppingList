package com.example.shoppinglist.repository;

import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryType name);
}
