package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.HashtagService;
import com.application.gentlegourmet.service.ProductService;
import com.application.gentlegourmet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final RecipeService recipeService;
    private final HashtagService hashtagService;
    private final CartService cartService;

    @GetMapping("/home")
    public String loadHome(Model model) {
        //Carousel : Bestsellers carousel
        List<Product> bestsellerProducts = productService.findTopFiveBestsellingProducts();
        //Carousel : Recommended For You
        List<Product> recommendedProducts = productService.findTopFiveRecommendedProducts();
        //Banner : Recipes
        Set<Recipe> recipes = recipeService.findAllRecipesNameAndProductOnly();
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        //HashtagService to provide top 5 hashtags
        List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();

        //Cart Items to show on top-nav widget
        Map<String, Object> cartsMap = cartService.listCartItems();
        if( !cartsMap.isEmpty() ) {
            List<Cart> carts = (List<Cart>) cartsMap.get("carts");
            int cartsTotalPrice = (Integer) cartsMap.get("cartsTotalPrice");
            model.addAttribute("carts", carts);
            model.addAttribute("cartsTotalPrice", cartsTotalPrice);
        }

        model.addAttribute("bestsellerProducts", bestsellerProducts);
        model.addAttribute("recommendedProducts", recommendedProducts);
        model.addAttribute("recipes", recipes);
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("hashtagList", hashtagList);

        return "home";
    }

}
