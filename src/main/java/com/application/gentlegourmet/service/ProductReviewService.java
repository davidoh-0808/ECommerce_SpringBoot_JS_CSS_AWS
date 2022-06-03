package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public int findProductRatingSumByProduct(Product product) {
        return productReviewRepository.findProductRatingSumByProduct(product);
    }



}
