package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.HashtagService;
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

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SearchService searchService;
    private final HashtagService hashtagService;
    private final CartService cartService;

    @GetMapping("/product/{productId}")
    public String getProductPage(@PathVariable Long productId, Model model) {

        //product here fetched productReviews via jpa and entity graph
        //in Product and ProductReviewService.. attach the review writer for each ProductReview
        Product product = productService.findProductById(productId);
        ProductSearch productSearch = new ProductSearch();

        int productReviewCount = product.getProductReviews().size();

        Map<String, Integer> ratingMap = product.getRatingMap();
        float averageRating = (
                (ratingMap.get("ratingFive") * 5) +
                (ratingMap.get("ratingFour") * 4) +
                (ratingMap.get("ratingThree") * 3) +
                (ratingMap.get("ratingTwo") * 2) +
                (ratingMap.get("ratingOne"))
            ) / (float) productReviewCount;

        //Cart Items to show on top-nav widget
        Map<String, Object> cartsMap = cartService.listCartItems();
        if( !cartsMap.isEmpty() ) {
            List<Cart> carts = (List<Cart>) cartsMap.get("carts");
            int cartsTotalPrice = (Integer) cartsMap.get("cartsTotalPrice");
            model.addAttribute("carts", carts);
            model.addAttribute("cartsTotalPrice", cartsTotalPrice);
        }

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
        //need a productSearch object(a DTO, not entity) for search request
        model.addAttribute("productSearch", productSearch);

        return "product/product";
    }


    //processes search from searchBarInput -> product-list page
    @PostMapping("/search")
    public String getProductListPage(@ModelAttribute ProductSearch productSearch, Model model) {

        ///////////////////////  main process ///////////////////////
        List<Product> searchResultProductList = searchService.findProductsByCategoryAndKeyword(productSearch);

        String failMessage = null;
        if(searchResultProductList.size() == 0) {
            failMessage = "Oops! We could not find a product that matches your query";
        }

        ///////////////////////  sending models for view rendering ///////////////////////
        //Cart Items to show on top-nav widget
        Map<String, Object> cartsMap = cartService.listCartItems();
        if( !cartsMap.isEmpty() ) {
            List<Cart> carts = (List<Cart>) cartsMap.get("carts");
            int cartsTotalPrice = (Integer) cartsMap.get("cartsTotalPrice");
            model.addAttribute("carts", carts);
            model.addAttribute("cartsTotalPrice", cartsTotalPrice);
        }
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();

        model.addAttribute("searchResultProductList", searchResultProductList);
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("failMessage", failMessage);
        model.addAttribute("hashtagList", hashtagList);

        return "product/product-list";
    }


    //processes search from hashtagList  -> product-list page
    @GetMapping("/search/{hashtag}")
    public String getProductListPage(@PathVariable String hashtag, Model model) {
        //search result List
        List<Product> searchResultProductList = searchService.findProductsByHashtag(hashtag);
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        //HashtagService to provide top 5 hashtags
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();

        model.addAttribute("searchResultProductList", searchResultProductList);
        model.addAttribute("hashtagSearched", hashtag);
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("hashtagList", hashtagList);

        return "product/product-list";
    }

    //toDO: sorting methods in progress..
    //processes the filtering of search results on product-list page
    @GetMapping("/search/most-sold")
    public String getProductListPageSortBySale(Model model) {
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        //HashtagService to provide top 5 hashtags
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();




        model.addAttribute("productSearch", productSearch);
        model.addAttribute("hashtagList", hashtagList);

        return "product/product-list";
    }
    //toDO: sorting methods in progress..
    @GetMapping("/search/price")
    public String getProductListPageSortByPrice(Model model) {
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        //HashtagService to provide top 5 hashtags
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();




        model.addAttribute("productSearch", productSearch);
        model.addAttribute("hashtagList", hashtagList);

        return "product/product-list";
    }
    //toDO: sorting methods in progress..
    @GetMapping("/search/rating")
    public String getProductListPageSortByRating(Model model) {
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        //HashtagService to provide top 5 hashtags
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();




        model.addAttribute("productSearch", productSearch);
        model.addAttribute("hashtagList", hashtagList);

        return "product/product-list";
    }


}
