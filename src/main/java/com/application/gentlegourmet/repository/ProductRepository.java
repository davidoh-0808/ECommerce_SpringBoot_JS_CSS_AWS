package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ProductRepository extends JpaRepository<Product, Long> {


    @Override
    @EntityGraph(
        value = "product-graph.category-brand-productReviews-productImages",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Optional<Product> findById(Long id);


    @EntityGraph(
        value = "product-graph.name-price-category",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT p FROM Product p")
    Set<Product> findAllProductsAndCategory();


}
