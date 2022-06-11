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

        System.out.println("***************** what is customerUsername ? : " + customerUsername);
        System.out.println("***************** is customer there? customer : " + customer);
        System.out.println("***************** made it here - customerCarts : " + customerCarts);

        for(Cart c : customerCarts) {
            System.out.println("**************** made it here 2 ****************");

            PurchaseDetail newPurchaseDetail = new PurchaseDetail();
            newPurchaseDetail.setPurchase(savedPurchase);
            newPurchaseDetail.setProduct( c.getProduct() );
            newPurchaseDetail.setQuantity( c.getQuantity() );

            System.out.println("**************** made it here 3 ****************");

            PurchaseDetail savedPurchaseDetail = purchaseDetailService.savePurchaseDetail(newPurchaseDetail);

            System.out.println("****************** savedPurchaseDetail : " + savedPurchaseDetail);
            System.out.println("****************** savedPurchaseDetail : " + savedPurchaseDetail.getPurchase());
            System.out.println("****************** savedPurchaseDetail : " + savedPurchaseDetail.getProduct());
            System.out.println("****************** savedPurchaseDetail : " + savedPurchaseDetail.getQuantity());

        }

        System.out.println("**************** made it here 4 ****************");

        //empty the cart after purchase is processed
        cartService.removeAllCartsByCustomerUsername(customerUsername);

        System.out.println("**************** made it here 5 ****************");

        return true;
    }


}
