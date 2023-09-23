package com.example.FlipCommerce.repository.Specifications;

import com.example.FlipCommerce.Enum.Category;
import com.example.FlipCommerce.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {

    public Specification<Product> getProductsByName(String productQuery) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%"+productQuery+"%");
            }
        };
    }

    public Specification<Product> getCategoryAndPriceGreater(Category category, int price) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate condition1 = criteriaBuilder.equal(root.get("category"), category);
                Predicate condition2 = criteriaBuilder.greaterThan(root.get("price"), price);

                return criteriaBuilder.and(condition1, condition2);
            }
        };
    }
}
