package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Brand;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Override
    @EntityGraph(
        value = "brand-graph.products",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Optional<Brand> findById(Long aLong);
}
