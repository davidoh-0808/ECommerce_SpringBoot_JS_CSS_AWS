package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("SELECT SUM(pr.rating) FROM ProductReview pr WHERE pr.product = :product ")
    int findProductRatingSumByProduct(@Param("product") Product product);

}
