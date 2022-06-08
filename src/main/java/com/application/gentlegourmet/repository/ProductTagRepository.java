package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    @Query("SELECT pt FROM ProductTag pt where pt.product = :product")
    Set<ProductTag> findProductTagsByProduct(@Param("product") Product product);

}
