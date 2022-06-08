package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductReview;
import com.application.gentlegourmet.entity.ProductSearch;
import com.application.gentlegourmet.service.ProductService;
import com.application.gentlegourmet.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SearchService searchService;

    @GetMapping("/product/{productId}")
    public String getProductPage(@PathVariable Long productId, Model model) {

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


    //processes search from searchBarInput -> product-list page
    @PostMapping("/search")
    public String getProductListPage(@ModelAttribute ProductSearch productSearch, Model model) {
        List<Product> searchResultProductList = searchService.findProductsByCategoryAndKeyword(productSearch);
        String failMessage = null;

        if(searchResultProductList.size() == 0) {
            failMessage = "Oops! We could not find a product that matches your query";
        }

        model.addAttribute("searchResultProductList", searchResultProductList);
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("failMessage", failMessage);

        return "product/product-list";
    }


    //processes search from hashtagList  -> product-list page
    @GetMapping("/search/{hashtag}")
    public String getProductListPage(@PathVariable String hashtag, Model model) {
        List<Product> searchResultProductList = searchService.findProductsByHashtag(hashtag);

        model.addAttribute("searchResultProductSet", searchResultProductList);
        model.addAttribute("hashtag", hashtag);

        return "product/product-list";
    }

    //toDO: sorting methods to do..
    //processes the filtering of search results on product-list page
    @GetMapping("/search/most-sold")
    public String getProductListPageSortBySale() {

        return "product/product-list";
    }

    @GetMapping("/search/price")
    public String getProductListPageSortByPrice() {

        return "product/product-list";
    }

    @GetMapping("/search/rating")
    public String getProductListPageSortByRating() {

        return "product/product-list";
    }


}
