package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final CustomerService customerService;
    private final PurchaseService purchaseService;
    private final PurchaseDetailService purchaseDetailService;


    ////////////////////////////////////////////////////////////////////////////////////


    public boolean completeCheckout(PaymentInfo paymentInfo) {
        Long purchaseId = Long.parseLong( paymentInfo.getMerchant_uid() );
        String customerUsername = paymentInfo.getBuyer_name();
        /*  obtaining the paid amount requires further validation with API server
            int cartsTotalPrice = Integer.parseInt( paymentInfo.getAmount() ); */

        /*
            get all user Carts & retrieve the saved Purchase from goToCheckout method
            and save new Purchase_Details
         */
        Customer customer = customerService.findCustomerByUsername(customerUsername);
        List<Cart> customerCarts = cartService.findCartsByCustomer(customer);
        Purchase savedPurchase = purchaseService.findPurchasebyId(purchaseId);

        for(Cart c : customerCarts) {
            PurchaseDetail newPurchaseDetail = new PurchaseDetail();
            newPurchaseDetail.setPurchase(savedPurchase);
            newPurchaseDetail.setProduct( c.getProduct() );
            newPurchaseDetail.setQuantity( c.getQuantity() );

            PurchaseDetail savedPurchaseDetail = purchaseDetailService.savePurchaseDetail(newPurchaseDetail);

        }

        //empty the cart after purchase is processed
        cartService.removeAllCartsByCustomerUsername(customerUsername);

        return true;
    }


}
