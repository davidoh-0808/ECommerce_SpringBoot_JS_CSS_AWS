package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Hashtag;
import com.application.gentlegourmet.entity.ProductSearch;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.HashtagService;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

            model.addAttribute("productSearch", productSearch);
            model.addAttribute("hashtagList", hashtagList);

            return "order/checkout";
        } else {
            redirectAttr.addFlashAttribute("errorMessage", "Please login before checking out items :)");

            return "redirect:" + referer;
        }

    }




}
