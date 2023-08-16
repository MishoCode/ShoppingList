package com.example.shoppinglist.controller;

import com.example.shoppinglist.dto.AddProductRequest;
import com.example.shoppinglist.dto.ProductResponse;
import com.example.shoppinglist.entity.CategoryType;
import com.example.shoppinglist.entity.Product;
import com.example.shoppinglist.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<AddProductRequest> addProduct(@Valid @RequestBody AddProductRequest request) {
        Product product = productService.addProduct(request);
        AddProductRequest responseBody = new AddProductRequest(
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getNeededBefore(),
            product.getCategory().getName());
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProductByCategory(
        @RequestParam("category") CategoryType categoryType) {
        List<Product> products = productService.getProductsByCategory(categoryType);
        List<ProductResponse> productResponseList = products.stream().map(ProductResponse::fromProduct).toList();
        return ResponseEntity.ok(productResponseList);
    }
}
