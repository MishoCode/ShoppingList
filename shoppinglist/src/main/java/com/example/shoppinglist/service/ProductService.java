package com.example.shoppinglist.service;

import com.example.shoppinglist.dto.AddProductRequest;
import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.CategoryType;
import com.example.shoppinglist.entity.Product;
import com.example.shoppinglist.exception.CategoryNotFoundException;
import com.example.shoppinglist.exception.ProductAlreadyExistsException;
import com.example.shoppinglist.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Transactional
    public Product addProduct(AddProductRequest request) throws CategoryNotFoundException {
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getNeededBefore());

        Optional<Product> productOptional = productRepository.findByName(product.getName());
        if (productOptional.isPresent()) {
            throw new ProductAlreadyExistsException(
                String.format("A product with name '%s' already exists.", product.getName()));
        }

        Category category = categoryService.findCategoryByName(request.getCategoryType());
        product.setCategory(category);


        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(CategoryType categoryType) {
        return productRepository.findAllByCategory(categoryType);
    }
}
