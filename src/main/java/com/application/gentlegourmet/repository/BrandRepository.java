package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Brand;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BrandRepository extends JpaRepository<Brand, Long> {

    /*
    @Override
    @EntityGraph(
        value = "brand-graph.products",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Optional<Brand> findById(Long Long);
    */

    @EntityGraph(
            value = "brand-graph.products",
            type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT b FROM Brand b WHERE b.id = :brandId")
    Brand findProductsByBrandId(@Param("brandId") Long brandId);


}
