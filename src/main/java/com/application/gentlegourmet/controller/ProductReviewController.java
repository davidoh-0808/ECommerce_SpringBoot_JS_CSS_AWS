package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.ProductReview;
import com.application.gentlegourmet.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    ////////////////////////////////////////////////////////////////////////////////////////////////


    @PostMapping("/review/{productId}")
    public String reviewProduct(@PathVariable Long productId, BindingResult result, @ModelAttribute ProductReview productReviewForForm, RedirectAttributes redirectAttrs/*, @RequestHeader String referer*/) {
        if(result.hasErrors()) {
            return "";
        }

        int rating = productReviewForForm.getRating();
        String review = productReviewForForm.getReview();
        String createdBy = productReviewForForm.getCreatedBy();
        Long purchaseId = productReviewForForm.getPurchaseId();

        //toDO : check if the purchaseId and createdBy matches the data in Purchase table & Customer table
        boolean isChecked = productReviewService.checkPurchaseIdAndUsernameFromDB(purchaseId, createdBy);

        if(isChecked) {
            //toDO : insert product review
            System.out.println("************** isChecked : " + isChecked);
        } else {
            throw new RuntimeException("The Product Review could not be inserted because.. the Username ("+createdBy+") and Purchase Id ("+purchaseId+") provided do not exist in the purchase order history");
        }

        //set redirect
        redirectAttrs.addAttribute("productId", productId).addFlashAttribute("successMessage", "Your Product Review was successfully submitted.");

        return "redirect:/product/{productId}";
    }

}
