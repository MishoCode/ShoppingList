package com.example.shoppinglist.repository;

import com.example.shoppinglist.entity.Category;
import com.example.shoppinglist.entity.CategoryType;
import com.example.shoppinglist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p where p.category.name=:categoryType")
    List<Product> findAllByCategory(@Param("categoryType")CategoryType categoryType);
}
