package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final ProductService productService;
    private final CartService cartService;
    private final HashtagService hashtagService;
    private final CustomerService customerService;
    private final PurchaseService purchaseService;

    @GetMapping("/checkout")
    public String goToCheckout(Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
        // common variable for page redirect
        String referer = request.getHeader("referer");
        // send ProductSearch model to view to allow search function
        ProductSearch productSearch = new ProductSearch();

        //1) check for authentication principal (aka. "is someone logged in?")
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //HashtagService to provide top 5 hashtags
            List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();

            //Cart Items to show on top-nav widget and on main section
            Map<String, Object> cartsMap = cartService.findCartItemsWithCategoryAndBrand();
            if( !cartsMap.isEmpty() ) {
                List<Cart> carts = (List<Cart>) cartsMap.get("carts");
                int cartsTotalPrice = (Integer) cartsMap.get("cartsTotalPrice");
                model.addAttribute("carts", carts);
                model.addAttribute("cartsTotalPrice", cartsTotalPrice);
            }

            //send the Customer username and the Purchase id (after creating one) to Payment JS API
            String customerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerService.findCustomerByUsername(customerUsername);
            Purchase purchase = new Purchase();
            purchase.setCustomer(customer);
            Purchase newPurchase = purchaseService.saveNewPurchase(purchase);
            String purchaseId = String.valueOf( newPurchase.getId() );

            model.addAttribute("productSearch", productSearch);
            model.addAttribute("hashtagList", hashtagList);
            model.addAttribute("customerUsername", customerUsername);
            model.addAttribute("purchaseId", purchaseId);

            return "order/checkout";
        } else {
            redirectAttr.addFlashAttribute("errorMessage", "Please login before checking out items :)");

            return "redirect:" + referer;
        }

    }



    @PostMapping("/checkout/complete")
    public String handleCheckout(@RequestBody PaymentInfo paymentInfo, Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
        System.out.println("*************** handleCheckout called ***************");
        // common variable for page redirect
        String referer = request.getHeader("referer");

        String impUid = paymentInfo.getImp_uid();
        String purchaseId = paymentInfo.getMerchant_uid();
        String customerUsername = paymentInfo.getBuyer_name();
        int cartsTotalPrice = paymentInfo.getAmount();

        //testing
        System.out.println("********************* purchaseId : " + purchaseId);

        //toDO
        //get all user Carts -> insert into a Purchase and Purchase_Details


        redirectAttr.addFlashAttribute("successMessage", "Payment Complete! Thank you :)");

        return "redirect:" + referer;
    }




}
