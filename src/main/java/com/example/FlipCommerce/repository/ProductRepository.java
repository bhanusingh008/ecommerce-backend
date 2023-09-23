package com.example.FlipCommerce.repository;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryAndPrice(Category category, int price);

    List<Product> findByCategory(Category category);
}
