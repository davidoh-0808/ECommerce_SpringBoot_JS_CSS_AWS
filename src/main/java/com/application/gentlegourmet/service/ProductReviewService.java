package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductReview;
import com.application.gentlegourmet.entity.Purchase;
import com.application.gentlegourmet.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    public int findProductRatingSumByProduct(Product product) {
        return productReviewRepository.findProductRatingSumByProduct(product);
    }


    //in Product and ProductReviewService.. attach the Product Review Creator for each Product Review
    public Set<ProductReview> attachProductReviewWriters(Set<ProductReview> productReviews) {
        for(ProductReview pr : productReviews) {
            Purchase purchase = pr.getPurchase();
            String customerUsername = purchase.getCustomer().getUsername();

            pr.setCreatedBy(customerUsername);
        }

        return productReviews;
    }


}
