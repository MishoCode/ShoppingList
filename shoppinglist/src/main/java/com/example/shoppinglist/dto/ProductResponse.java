package com.example.shoppinglist.dto;

import com.example.shoppinglist.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {
    private String name;

    private String description;

    private BigDecimal price;

    private LocalDateTime neededBefore;

    private String categoryType;

    public static ProductResponse fromProduct(Product p) {
        return new ProductResponse(
            p.getName(),
            p.getDescription(),
            p.getPrice(),
            p.getNeededBefore(),
            p.getCategory().getName().name()
        );
    }
}
