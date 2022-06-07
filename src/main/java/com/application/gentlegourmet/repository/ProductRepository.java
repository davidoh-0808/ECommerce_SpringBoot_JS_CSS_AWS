package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(
        value = "product-graph.all-fields",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Optional<Product> findById(Long id);


    @EntityGraph(
        value = "product-graph.name-price-category",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT p FROM Product p")
    Set<Product> findAllProductsAndCategory();


    @EntityGraph(
        value = "product-graph.name",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT p FROM Product p where p.recipes in (:recipe)")
    Product findProductByRecipe(@Param("recipe") Recipe recipe);

}
