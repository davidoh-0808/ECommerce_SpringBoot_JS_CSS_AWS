package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.PurchaseDetail;
import com.application.gentlegourmet.repository.PurchaseDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PurchaseDetailService {

    private final PurchaseDetailRepository purchaseDetailRepository;

    //////////////////////////////////////////////////////////////////////////////////


    public int findSaleQuantitySumByProduct(Product product) {

        return purchaseDetailRepository.findSaleQuantitySumByProduct(product);
    }


    public PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail) {

        return purchaseDetailRepository.save(purchaseDetail);
    }


    public Map<String, Integer> findTotalSalesQuantityAndAmount() {
        Map<String, Integer> totalSalesMap = new HashMap<>();
        int totalSalesQuantity = 0;
        int totalSalesAmount = 0;

        Set<PurchaseDetail> purchaseDetailSet = purchaseDetailRepository.findAllPurchaseDetails();
        for(PurchaseDetail pd : purchaseDetailSet) { //for each pd = one product x qty
            int price = pd.getProduct().getPrice();
            int quantity = pd.getQuantity();
            totalSalesQuantity += quantity;
            totalSalesAmount += (price * quantity);
        }
        totalSalesMap.put("totalSalesQuantity", totalSalesQuantity);
        totalSalesMap.put("totalSalesAmount", totalSalesAmount);

        return totalSalesMap;
    }

}
