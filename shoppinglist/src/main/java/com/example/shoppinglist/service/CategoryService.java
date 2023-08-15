package com.example.shoppinglist.service;

import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.CategoryType;
import com.example.shoppinglist.exception.CategoryNotFoundException;
import com.example.shoppinglist.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    Category findCategoryByName(CategoryType name) {
        return categoryRepository.findByName(name).orElseThrow(
            () -> new CategoryNotFoundException("This category does not exist!"));
    }
}
