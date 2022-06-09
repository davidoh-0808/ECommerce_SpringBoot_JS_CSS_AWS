package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductReview;
import com.application.gentlegourmet.entity.Purchase;
import com.application.gentlegourmet.service.ProductReviewService;
import com.application.gentlegourmet.service.ProductService;
import com.application.gentlegourmet.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;
    private final PurchaseService purchaseService;
    private final ProductService productService;

    ////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/review/{productId}")
    public String reviewProduct(@PathVariable Long productId, @ModelAttribute ProductReview productReviewForForm, BindingResult result, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "/home";
        }

        int rating = productReviewForForm.getRating();
        String review = productReviewForForm.getReview();
        String createdBy = productReviewForForm.getCreatedBy();
        Long purchaseId = productReviewForForm.getPurchaseId();

        //first, check purchase history to validify the submitted purchase id and username
        boolean isChecked = productReviewService.checkPurchaseIdAndUsernameFromDB(purchaseId, createdBy);

        if(isChecked) {
            //insert product review
            Purchase purchase = purchaseService.findPurchasebyId(purchaseId);
            Product product = productService.findProductById(productId);

            ProductReview productReview = new ProductReview();
            productReview.setRating(rating);
            productReview.setReview(review);
            productReview.setPurchase(purchase);
            productReview.setProduct(product);

            productReviewService.saveProductReview(productReview);

        } else {
            throw new RuntimeException("The Product Review could not be inserted because.. the Username ("+createdBy+") and Purchase Id ("+purchaseId+") provided do not exist in the purchase order history");
        }

        //config redirect and success message
        redirectAttrs.addAttribute("productId", productId).addFlashAttribute("successMessage", "Your Product Review was successfully submitted.");

        return "redirect:/product/{productId}";
    }

}
