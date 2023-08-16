package com.example.shoppinglist.init;

import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.CategoryType;
import com.example.shoppinglist.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryInit {

    private CategoryRepository categoryRepository;

    @PostConstruct
    public void addCategoriesToTheDatabase() {
        if (categoryRepository.count() == 0) {
            Category foodCategory = new Category(
                CategoryType.FOOD,
                "Here are stored the foods you would like to buy");

            Category drinkCategory = new Category(
                CategoryType.DRINK,
                "Here are stored the drinks you would like to buy"
            );

            Category householdCategory = new Category(
                CategoryType.HOUSEHOLD,
                "Here are stored the household related products you would like to buy"
            );

            Category otherCategory = new Category(
                CategoryType.OTHER,
                "All other stuff you would like to buy"
            );

            categoryRepository.saveAll(List.of(foodCategory, drinkCategory, householdCategory, otherCategory));
        }
    }
}
