package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Purchase;
import com.application.gentlegourmet.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<String, Date> findFirstAndLastPurchaseDate() {
        Map<String, Date> purchaseDateMap = new HashMap<>();

        List<Purchase> purchases = purchaseRepository.findAllPurchaseOrderByCreated();
        purchaseDateMap.put("firstPurchaseDate", purchases.get(0).getCreated());
        purchaseDateMap.put("lastPurchaseDate", purchases.get( purchases.size() -1 ).getCreated());

        return purchaseDateMap;
    }

}
