package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.PurchaseDetail;
import com.application.gentlegourmet.repository.PurchaseDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseDetailService {

    private final PurchaseDetailRepository purchaseDetailRepository;


    public int findSaleQuantitySumByProduct(Product product) {

        return purchaseDetailRepository.findSaleQuantitySumByProduct(product);
    }

}
