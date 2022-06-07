package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductReview;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/{productId}")
    public String showProductPage(@PathVariable Long productId, Model model) {

        //product here fetched productReviews via jpa and entity graph
        //in Product and ProductReviewService.. attach the review writer for each ProductReview
        Product product = productService.findProductById(productId);

        int productReviewCount = product.getProductReviews().size();

        Map<String, Integer> ratingMap = product.getRatingMap();
        float averageRating = (
                (ratingMap.get("ratingFive") * 5) +
                (ratingMap.get("ratingFour") * 4) +
                (ratingMap.get("ratingThree") * 3) +
                (ratingMap.get("ratingTwo") * 2) +
                (ratingMap.get("ratingOne"))
            ) / (float) productReviewCount;

        //send Product Detail related model
        model.addAttribute("product", product);
        model.addAttribute("category", product.getCategory().getName());
        model.addAttribute("productImages", product.getProductImages());
        model.addAttribute("productReviews", product.getProductReviews());
        //send ProductReview and rating related models
        model.addAttribute("productReviewCount", productReviewCount);
        model.addAttribute("ratingFive", ratingMap.get("ratingFive"));
        model.addAttribute("ratingFour", ratingMap.get("ratingFour"));
        model.addAttribute("ratingThree", ratingMap.get("ratingThree"));
        model.addAttribute("ratingTwo", ratingMap.get("ratingTwo"));
        model.addAttribute("ratingOne", ratingMap.get("ratingOne"));
        model.addAttribute("averageRating", averageRating);
        //also send ProductReview model for the post form of ProductReview
        model.addAttribute("productReviewForForm", new ProductReview());

        return "product/product";
    }

}
