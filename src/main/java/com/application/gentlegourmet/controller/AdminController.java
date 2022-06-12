package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.ProductSearch;
import com.application.gentlegourmet.service.PurchaseDetailService;
import com.application.gentlegourmet.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final PurchaseDetailService purchaseDetailService;
    private final PurchaseService purchaseService;

///////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/admin-signin")
    public String getAdminPage(Model model) {
        ////////////////////////////////// main process /////////////////////////////
        Map<String, Integer> totalSalesMap = purchaseDetailService.findTotalSalesQuantityAndAmount();
        Map<String, Date> purchaseDateMap = purchaseService.findFirstAndLastPurchaseDate();


        //////////////////////  sending models for view rendering ///////////////////
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("totalSalesQuantity", totalSalesMap.get("totalSalesQuantity"));
        model.addAttribute("totalSalesAmount",totalSalesMap.get("totalSalesAmount"));
        model.addAttribute("firstPurchaseDate", purchaseDateMap.get("firstPurchaseDate"));
        model.addAttribute("lastPurchaseDate", purchaseDateMap.get("lastPurchaseDate"));

        return "admin/sales";
    }


}
