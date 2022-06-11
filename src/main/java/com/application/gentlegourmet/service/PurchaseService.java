package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Purchase;
import com.application.gentlegourmet.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;


    ///////////////////////////////////////////////////////////////////////////////////


    /*
    public Purchase findPurchaseByProductReview(ProductReview productReview) {

        return purchaseRepository.findPurchaseByProductReview(productReview);
    }
     */


    public Purchase findPurchasebyId(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).get();
    }


    public Purchase saveNewPurchase(Purchase purchase) {

        return purchaseRepository.save(purchase);
    }

}
