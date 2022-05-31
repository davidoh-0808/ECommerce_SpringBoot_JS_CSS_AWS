package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    //override a default CRUD method & refer to a named entity graph
    @Override
    @EntityGraph(
        value = "product-graph.category-brand-productReviews-productImages",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Optional<Product> findById(Long id);


}
