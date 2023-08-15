package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.AddProductRequest;
import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.Product;
import com.example.shoppinglist.exception.CategoryNotFoundException;
import com.example.shoppinglist.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    public Product addProduct(AddProductRequest request) throws CategoryNotFoundException {
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getNeededBefore());
        Category category = categoryService.findCategoryByName(request.getCategoryType());
        product.setCategory(category);

        return productRepository.save(product);
    }
}
